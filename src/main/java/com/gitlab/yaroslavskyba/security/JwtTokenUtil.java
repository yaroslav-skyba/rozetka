package com.gitlab.yaroslavskyba.security;

import com.gitlab.yaroslavskyba.Main;
import com.gitlab.yaroslavskyba.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String JWT_SECRET = "qYrYaF92NupxVj-LnfqTXmdfoi19Zf-KpPIkUw-YzEWDRK32uRKpwHbH139__8XK";

    public String generateAccessToken(UserDto userDto) {
        return Jwts.builder().setSubject(userDto.getLogin()).setIssuer(Main.class.getPackageName()).setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 10_000_000_000L)).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException signatureException) {
            return false;
        }
    }
}
