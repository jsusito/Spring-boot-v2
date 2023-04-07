package com.tokioschool.web.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    final private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/film/**","/user/**", "/person/**", "/login", "/logout", "/index")
            .authorizeHttpRequests(authorizeRequest ->
                    authorizeRequest
                    .requestMatchers("/login", "logout", "/index","/user/**").permitAll()
                    .requestMatchers("/film/**", "/person/**").authenticated())
            .formLogin(login -> login
                    .loginPage(Constant.LOGIN_URL)
                    .successHandler(authenticationSuccessHandler)
                    .failureUrl(Constant.LOGIN_FAILURE_URL))
            .logout(logout -> logout
                    .logoutSuccessUrl(Constant.LOGOUT_SUCCESS_URL)
                    .logoutRequestMatcher(new AntPathRequestMatcher(Constant.LOGOUT_URL)));

        return http.build();
    }

}


