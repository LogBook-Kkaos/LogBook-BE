package com.logbook.backend.logbookbe.global.jwt;

import com.logbook.backend.logbookbe.global.auth.AuthDetailsService;
import com.logbook.backend.logbookbe.global.jwt.detail.AuthRole;
import com.logbook.backend.logbookbe.global.jwt.exception.ExpiredTokenException;
import com.logbook.backend.logbookbe.global.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final String accessSecret;
    private final String refreshSecret;
    private final AuthDetailsService authDetailsService;
    @Autowired
    public JwtProvider(@Value("${jwt.access_secret}") String accessSecret,
                       @Value("${jwt.refresh_secret}") String refreshSecret,
                       AuthDetailsService authDetailsService) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.authDetailsService = authDetailsService;
    }
    public String generateToken(String email, AuthRole role, boolean isRefreshToken) {
        Instant accessDate = LocalDateTime.now().plusHours(6).atZone(ZoneId.systemDefault()).toInstant();
        Instant refreshDate = LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .claim("role", role.getRole())
                .setSubject(email)
                .setExpiration(isRefreshToken ? Date.from(refreshDate) : Date.from(accessDate))
                .signWith(SignatureAlgorithm.HS256, isRefreshToken ? refreshSecret : accessSecret)
                .compact();
    }
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token, false);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(claims.get("role").toString()));
        UserDetails principal = this.authDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    public void validateToken(String token, boolean isRefreshToken) {
        try {
            parseClaims(token, isRefreshToken);
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }
    public Claims parseClaims(String accessToken, boolean isRefreshToken) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(isRefreshToken ? refreshSecret : accessSecret);
            return parser.parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}