package uz.pdp.appnewssiteindependent.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssiteindependent.entity.Role;
import uz.pdp.appnewssiteindependent.entity.enums.Permission;

import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private final String secretWord = "ThisIsMySecretSentence1q2w3e4r5t6y7u8i9o0p";

    public String generateToken(String username, List<Permission> permissions){
        long expireTime = 24 * 3600 * 1000; //one day

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("permissions", permissions)
                .signWith(SignatureAlgorithm.HS512, secretWord)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token){
        try {
            String subject = Jwts
                    .parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return subject;
        }catch (Exception e){
            return null;
        }
    }

}
