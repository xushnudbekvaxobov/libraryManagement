package project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.dto.requestDto.JwtDto;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private  int tokenLifeTime;

    @Value("${jwt.secret.key}")
    private  String secretKey;

    public  String encode(String username, String role) {
        Map<String,String> extractions = Map.of("role", role);
        return Jwts
                .builder()
                .claims(extractions)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLifeTime))
                .signWith(getSignKey())
                .compact();
    }

    public  JwtDto decode(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        String role = (String) claims.get("role");
        return new JwtDto(username, role);
    }
    private  SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

