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
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.PATCH, "/auth").permitAll()
            .antMatchers(HttpMethod.DELETE, "/auth").authenticated()
            .antMatchers(HttpMethod.POST, "/auth/signup").permitAll()
            .antMatchers(HttpMethod.PATCH, "/auth/password/initialize").permitAll()
            .antMatchers(HttpMethod.PATCH, "/auth/image").permitAll()
            .antMatchers(HttpMethod.DELETE, "/auth/image").permitAll()

            // Email
            .antMatchers(HttpMethod.POST, "/email").permitAll()
            .antMatchers(HttpMethod.GET, "/email/authentication").permitAll()
            .antMatchers(HttpMethod.GET, "/email").permitAll()

            // Client
            .antMatchers(HttpMethod.GET, "/client").authenticated()
            .antMatchers(HttpMethod.POST, "/client").authenticated()
            .antMatchers(HttpMethod.GET, "/client/{id}").authenticated()
            .antMatchers(HttpMethod.PATCH, "/client/{id}").authenticated()
            .antMatchers(HttpMethod.DELETE, "/client/{id}").authenticated()
            .antMatchers(HttpMethod.GET, "/client/search").hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/client/{id}/patch").hasRole("ADMIN")

            // Admin
            .antMatchers(HttpMethod.POST, "/admin/parsing-member").hasRole("ADMIN")

            // OAuth
            .antMatchers(HttpMethod.POST, "/oauth/code").permitAll()
            .antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
            .antMatchers(HttpMethod.PATCH, "/oauth/code").permitAll()
            .antMatchers(HttpMethod.GET, "/oauth/{clientId}").permitAll()
            .antMatchers(HttpMethod.POST, "/oauth/code/access").authenticated()

            // User
            .antMatchers("/user/**").authenticated()
            .antMatchers(HttpMethod.PATCH, "/user/accept-teacher").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/user/pending").hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/user/accept-student").hasRole("ADMIN")

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
