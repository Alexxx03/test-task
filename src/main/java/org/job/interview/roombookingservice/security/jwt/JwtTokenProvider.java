package org.job.interview.roombookingservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.job.interview.roombookingservice.exceptions.InvalidJwtException;
import org.job.interview.roombookingservice.security.AuthenticatedUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expirationPeriod;
    SecretKey secretKey;
    private JwtParser jwtParser;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        this.secretKey = decodeKeyFromString(secret);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey).build();
    }

    public SecretKey decodeKeyFromString(String keyStr) {
        byte[] decodedKey = Base64.getDecoder().decode(keyStr);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public Authentication getAuthentication(String token) {
        String username = jwtParser.parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (userDetails instanceof AuthenticatedUserDetails) {
            return new JwtAuthentication((AuthenticatedUserDetails) userDetails, token, userDetails.getAuthorities());
        } else {
            throw new InvalidJwtException(HttpStatus.FORBIDDEN, "Jwt is invalid", token);
        }
    }

    public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(authorities));

        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationPeriod);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException(HttpStatus.FORBIDDEN, "Jwt is invalid", token);
        }
    }

    private List<String> getRoleNames(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
