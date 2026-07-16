package dgi.nifonline.backend.utils;

import org.springframework.stereotype.Component; 
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; 
import java.util.Date; 

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
