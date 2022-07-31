package com.msg.gauth.global.security.jwt;

import com.msg.gauth.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;

    static final String ACCESS_TYPE = "access";
    static final String REFRESH_TYPE = "refresh";
    static final Long ACCESS_EXP = 60L * 15; // 15 min
    static final Long REFRESH_EXP = 60L * 60 * 24 * 7; // 1 weeks
    static final String TOKEN_PREFIX = "Bearer ";

    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS_TYPE, jwtProperties.getAccessSecret(), ACCESS_EXP);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, REFRESH_TYPE, jwtProperties.getRefreshSecret(), REFRESH_EXP);
    }

    public String resolveToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        return parseToken(token);
    }

    public String resolveRefreshToken(HttpServletRequest req) {
        String token = req.getHeader("Refresh-Token");
        return parseToken(token);
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String parseToken(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX))
            return token.replace(TOKEN_PREFIX, "");
        return null;
    }

    public ZonedDateTime getAccessExpiredTime() {
        return ZonedDateTime.now().plusSeconds(ACCESS_EXP);
    }

    public String generateToken(String sub, String type, String secret, Long exp) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret)
                .setSubject(sub)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    private Claims getTokenBody(String token, String secret) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // TODO: throw Expired Token Exception
            throw new RuntimeException();
        } catch (Exception e) {
            // TODO: throw Invalid Token Exception
            throw new RuntimeException();
        }
    }

    private String getTokenSubject(String token, String secret) {
        return getTokenBody(token, secret).getSubject();
    }
}