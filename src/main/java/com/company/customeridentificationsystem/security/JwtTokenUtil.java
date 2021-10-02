package com.company.customeridentificationsystem.security;

import com.company.customeridentificationsystem.model.dao.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import static java.lang.String.format;

@Slf4j
@Component
public class JwtTokenUtil {

    private final static String JWT_SECRET = "jeghHb2rw43Ngewg24N3242nojn";
    private final static String JWT_ISSUER = "example.io";

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(1L).toInstant()))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJwt(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
