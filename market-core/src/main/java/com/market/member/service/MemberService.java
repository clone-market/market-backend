package com.market.member.service;

import com.market.mail.dto.EmailMessageParam;
import com.market.mail.service.EmailService;
import com.market.member.dto.AddressCreationParam;
import com.market.member.dto.MemberCreationParam;
import com.market.member.dto.TermsCreationParam;
import com.market.member.entity.*;
import com.market.member.repository.AddressRepository;
import com.market.member.repository.MemberGradeRepository;
import com.market.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final EmailService emailService;

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final MemberGradeRepository memberGradeRepository;

    private final CacheManager cacheManager;
    private final TemplateEngine templateEngine;

    private static final String NORMAL_GRADE = "normal";
    private static final String CACHE_NAME = "memberAuth";
    private static final long EXPIRATION_MINUTES = 3;


    @Transactional
//    @PostConstruct
    public void initDb() {
        memberGradeRepository.save(new MemberGrade(NORMAL_GRADE, 0));
    }

    @Transactional
    public Long signUp(MemberCreationParam memberCreationParam) {
        if (memberRepository.findByEmail(memberCreationParam.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Member member = createMember(memberCreationParam,
                getNormalGrade(), createMemberTerms(memberCreationParam.getTermsCreationParam()));

        createAddress(member, memberCreationParam.getAddressCreationParam());
        return member.getId();
    }


    @Transactional
    public void changePassword(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        if (!isValidatedEmail(email)) {
            throw new IllegalStateException("이메일 인증을 하지 않았습니다.");
        }
        Member member = optionalMember.get();
        // TODO: 2021-06-17[양동혁] 패스워드 암호화
        member.changePassword(password);
    }

    public String generateAuthCode(String email) {
        String authCode = RandomStringUtils.randomNumeric(6);
        getCache().put(email, new AuthCode(authCode, LocalDateTime.now()));
        emailService.send(createEmailMessage(authCode, email));
        return authCode;
    }

    /**
     * @return 올바른 인증코드일 경우에만 true반환
     */
    public boolean validateAuthCode(String email, String code) {
        AuthCode cachedAuthCode = getCachedAuthCode(email, getCache());
        if (isValidatedAuthCode(code, cachedAuthCode)) {
            cachedAuthCode.updateToValidated();
            return true;
        }
        return false;
    }

    public boolean isValidatedEmail(String email) {
        AuthCode authCode = getCache().get(email, AuthCode.class);
        return authCode != null && authCode.isValidated();
    }

    private EmailMessageParam createEmailMessage(String authCode, String email) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        context.setVariable("message", "이메일 인증번호 입니다.");

        String message = templateEngine.process("mail/email-form", context);
        return new EmailMessageParam(email, "kurly-clone, 이메일 인증", message);
    }

    private void createAddress(Member member, AddressCreationParam addressCreationParam) {
        addressRepository.save(Address.builder()
                .zipCode(addressCreationParam.getZipCode())
                .streetNameAddress(addressCreationParam.getStreetNameAddress())
                .lotNumberAddress(addressCreationParam.getLotNumberAddress())
                .detailedAddress(addressCreationParam.getDetailedAddress())
                .isBasicAddress(true)
                .member(member)
                .build());
    }

    // TODO: 2021-06-17[양동혁] 패스워드 암호화
    private Member createMember(MemberCreationParam memberCreationParam, MemberGrade grade, MemberTerms terms) {
        if (terms.getConsentOptYn() == Yn.N) {
            memberCreationParam.setBirthday(LocalDateTime.of(1900, 1, 1, 0 ,0));
            memberCreationParam.setGender(Gender.N);
        }

        Member member = Member.builder()
                .username(memberCreationParam.getUsername())
                .password(memberCreationParam.getPassword())
                .name(memberCreationParam.getName())
                .email(memberCreationParam.getEmail())
                .phoneNumber(memberCreationParam.getPhoneNumber())
                .gender(memberCreationParam.getGender())
                .birthDay(memberCreationParam.getBirthday())
                .terms(terms)
                .grade(grade)
                .build();
        return memberRepository.save(member);
    }

    private MemberTerms createMemberTerms(TermsCreationParam termsCreationParam) {
        return MemberTerms.builder()
                .agreeYn(termsCreationParam.getAgreeYn())
                .consentReqYn(termsCreationParam.getConsentReqYn())
                .consentOptYn(termsCreationParam.getConsentOptYn())
                .mailYn(termsCreationParam.getMailYn())
                .smsYn(termsCreationParam.getSmsYn())
                .fourteenYn(termsCreationParam.getFourteenYn())
                .build();
    }

    private MemberGrade getNormalGrade() {
        return memberGradeRepository.findByName(NORMAL_GRADE)
                .orElseThrow(() -> new IllegalArgumentException(String.format("'%s'는 없는 등급입니다", NORMAL_GRADE)));
    }

    private Cache getCache() {
        return cacheManager.getCache(CACHE_NAME);
    }

    private AuthCode getCachedAuthCode(String email, Cache memberAuthCache) {
        return memberAuthCache.get(email, AuthCode.class);
    }

    private boolean isValidatedAuthCode(String authCode, AuthCode cachedAuthCode) {
        return cachedAuthCode != null &&
                cachedAuthCode.getAuthCode().equals(authCode) &&
                cachedAuthCode.generatedAt.isAfter(LocalDateTime.now().minusMinutes(EXPIRATION_MINUTES));
    }

    @Getter
    @RequiredArgsConstructor
    private static class AuthCode implements Serializable {
        private final String authCode;
        private final LocalDateTime generatedAt;
        private boolean validated = false;

        public void updateToValidated() {
            validated = true;
        }
    }
}
