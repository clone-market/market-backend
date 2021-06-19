package com.market.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.api.security.SkipPathRequestMatcher;
import com.market.api.security.filter.AsyncLoginProcessingFilter;
import com.market.api.security.filter.JwtTokenAuthenticationProcessingFilter;
import com.market.api.security.provider.AsyncAuthenticationProvider;
import com.market.api.security.provider.JwtAuthenticationProvider;
import com.market.api.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/v2/user/signIn";
    public static final String SIGN_UP_URL = "/api/v2/user/signUp";
    public static final String API_ROOT_URL = "/api/v2/**";

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final AsyncAuthenticationProvider asyncAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(buildAsyncLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(asyncAuthenticationProvider);
    }
    private AsyncLoginProcessingFilter buildAsyncLoginProcessingFilter() throws Exception {
        AsyncLoginProcessingFilter filter = new AsyncLoginProcessingFilter(AUTHENTICATION_URL, objectMapper, successHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(getPermitAllPathList(), API_ROOT_URL);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher, failureHandler, jwtUtil);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
    private List<String> getPermitAllPathList() {
        return Arrays.asList(
                AUTHENTICATION_URL,
                SIGN_UP_URL
        );
    }
}
