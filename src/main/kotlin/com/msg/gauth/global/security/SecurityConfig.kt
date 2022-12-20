package com.msg.gauth.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.global.security.config.FilterConfig
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors().and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(RequestMatcher { request ->
                CorsUtils.isPreFlightRequest(request)
            }).permitAll()

            // Auth
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.PATCH, "/auth").permitAll()
            .antMatchers(HttpMethod.DELETE, "/auth").authenticated()
            .antMatchers(HttpMethod.POST, "/auth/signup").permitAll()
            .antMatchers(HttpMethod.PATCH, "/auth/password/initialize").permitAll()

            // Email
            .antMatchers(HttpMethod.POST, "/email").permitAll()
            .antMatchers(HttpMethod.GET, "/email/authentication").permitAll()
            .antMatchers(HttpMethod.GET, "/email").permitAll()

            // Client
            .antMatchers(HttpMethod.GET, "/client").authenticated()
            .antMatchers(HttpMethod.POST, "/client").authenticated()
            .antMatchers(HttpMethod.GET, "/client/{id}").authenticated()
            .antMatchers(HttpMethod.PATCH, "/client/{id}").authenticated()

            // Admin
            .antMatchers("/admin/**").hasRole("ADMIN")

            // OAuth
            .antMatchers("/oauth/**").permitAll()

            //User
            .antMatchers("/user/**").authenticated()

            .anyRequest().denyAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))

            .and()
            .apply(FilterConfig(jwtTokenProvider, objectMapper))

            .and()
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()
}