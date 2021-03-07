package com.jwtcrud.api.security.jwt;

import com.jwtcrud.api.security.model.UsuarioPrincipal;
import com.jwtcrud.api.security.util.Constantes;
import com.jwtcrud.api.security.util.JwtDto;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ Constantes.EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, Constantes.YOUR_SECRET.getBytes())
                .compact();
    }

    public String refreshToken(JwtDto jwtDto) throws ParseException {
        JWT jwt = JWTParser.parse(jwtDto.getToken());
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String username = claims.getSubject();
        List<String> roles = (List<String>) claims.getClaim("roles");
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ Constantes.EXPIRATION * 2))
                .signWith(SignatureAlgorithm.HS256, Constantes.YOUR_SECRET.getBytes())
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(Constantes.YOUR_SECRET.getBytes())
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(Constantes.YOUR_SECRET.getBytes()).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException m){
            LOGGER.error("Token Mal Formado");
        }catch (UnsupportedJwtException u){
            LOGGER.error("Token No Soportado");
        }catch (ExpiredJwtException e){
            LOGGER.error("Token Expirado");
        }catch (IllegalArgumentException i){
            LOGGER.error("Token Vacío");
        }catch (SignatureException s){
            LOGGER.error("Fallo en la Firma");
        }
        return false;
    }
}
