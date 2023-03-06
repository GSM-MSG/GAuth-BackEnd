package com.msg.gauth.global.security

import com.fasterxml.jackson.databind.ObjectMapper
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
            .mvcMatchers(HttpMethod.POST, "/auth").permitAll()
            .mvcMatchers(HttpMethod.PATCH, "/auth").permitAll()
            .mvcMatchers(HttpMethod.DELETE, "/auth").authenticated()
            .mvcMatchers(HttpMethod.POST, "/auth/signup").permitAll()
            .mvcMatchers(HttpMethod.PATCH, "/auth/password/initialize").permitAll()
            .mvcMatchers(HttpMethod.PATCH, "/auth/image").permitAll()
            .mvcMatchers(HttpMethod.DELETE, "/auth/image").permitAll()

            // Email
            .mvcMatchers(HttpMethod.POST, "/email").permitAll()
            .mvcMatchers(HttpMethod.GET, "/email/authentication").permitAll()
            .mvcMatchers(HttpMethod.GET, "/email").permitAll()

            // Client
            .mvcMatchers(HttpMethod.GET, "/client").authenticated()
            .mvcMatchers(HttpMethod.POST, "/client").authenticated()
            .mvcMatchers(HttpMethod.GET, "/client/{id}").authenticated()
            .mvcMatchers(HttpMethod.PATCH, "/client/{id}").authenticated()
            .mvcMatchers(HttpMethod.GET, "/client/search").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.PATCH, "/client/{id}/patch").hasRole("ADMIN")

            // Admin
            .mvcMatchers("/admin/**").hasRole("ADMIN")

            // OAuth
            .mvcMatchers("/oauth/**").permitAll()

            // User
            .mvcMatchers("/user/**").authenticated()
            .mvcMatchers(HttpMethod.PATCH, "/user/accept-teacher").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.GET, "/user/pending").hasRole("ADMIN")

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