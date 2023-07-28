package com.logbook.backend.logbookbe.global.security;

import com.logbook.backend.logbookbe.global.filter.ExceptionHandleFilter;
import com.logbook.backend.logbookbe.global.jwt.AuthRole;
import com.logbook.backend.logbookbe.global.jwt.JwtFilter;
import com.logbook.backend.logbookbe.global.jwt.JwtProvider;
import com.logbook.backend.logbookbe.global.oauth.OAuthDetailService;
import com.logbook.backend.logbookbe.global.oauth.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtProvider jwtProvider;
    private final OAuthDetailService oAuthDetailService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()

                .antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/refresh").permitAll()
                .antMatchers("/api/user/register").permitAll()
                .antMatchers("/api/projects/**").hasAuthority(AuthRole.ROLE_ADMIN.getRole())
                .anyRequest().authenticated()
                .and()

                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .redirectionEndpoint()
                .baseUri("/oauth/callback/**")
                .and()
                .authorizationEndpoint()
                .baseUri("/oauth/login")
                .and()
                .userInfoEndpoint()
                .userService(oAuthDetailService)
                .and().and()

                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandleFilter(), JwtFilter.class)
                .build();
    }
}
