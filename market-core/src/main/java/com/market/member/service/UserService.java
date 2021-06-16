package com.market.member.service;

import com.market.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    private final CacheManager cacheManager;

    private static final String CACHE_NAME = "memberAuth";
    private static final long EXPIRATION_MINUTES = 3;

    public String generateAuthCode(String email) {
        String authCode = RandomStringUtils.randomNumeric(6);
        // TODO: 2021-06-17[양동혁] 콘솔출력, 이메일전송으로 분리
        log.info(authCode);
        getCache().put(email, new AuthCode(authCode, LocalDateTime.now()));
        return authCode;
    }

    /**
     * @return 올바른 인증코드일 경우에만 true반환
     */
    public boolean validateAuthCode(String email, String code) {
        AuthCode cachedAuthCode = getCachedAuthCode(email, getCache());
        if (isValidatedAuthCode(code, cachedAuthCode)) {
            getCache().evictIfPresent(email);
            return true;
        }
        return false;
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
    }
}
