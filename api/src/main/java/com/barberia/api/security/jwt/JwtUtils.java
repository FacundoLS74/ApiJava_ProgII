package com.barberia.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey; // Ayuda a firmar el token para que sea válido
    @Value("${jwt.time.expiration}")
    private String timeExpiration; // Cuanto tiempo de validez se le da al token

    // Generar token de acceso
    public String generateAccesToken(String username){
        return Jwts.builder()
                .setSubject(username) // el usuario que va a generar el token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creacion del token
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration))) // Cuando expirará el token
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256) // Firma
                .compact();
    }

    // Validar el token de acceso
    public boolean isTokenValid(String token){
        try{
            Jwts.parser()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch(Exception e){
            log.error("Token invalido, error: ".concat(e.getMessage()));
            return false;
        }
    }

    // Obtener el username del token
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    // Obtener un solo claim
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token); // Obtenemos todos los claims
        return claimsTFunction.apply(claims);
    }

    // Obtener todos los claims del token
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener firma del token
    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodifica la clave
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
