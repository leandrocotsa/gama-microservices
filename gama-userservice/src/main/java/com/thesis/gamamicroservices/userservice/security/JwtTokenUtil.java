package com.thesis.gamamicroservices.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.token.expirationTimeSecs}")
    public long jwtTokenValidity;

    @Value("${jwt.secret}")
    private String secret;

    public String getEmailFromAuthorizationString(String authorizationToken) {
        return getClaimFromToken(authorizationToken.substring(7), Claims::getSubject);
    }
    public String getUserIdFromAuthorizationString(String authorizationToken) {
        return getClaimFromToken(authorizationToken.substring(7), Claims::getId);
    }

    //retrieve email from jwt token
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public String getUserRoleFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails, int userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        String completeRole = "ROLE_" + role;
        //userDetails.getAuthorities().
        //claims.put("roles", role);
        return doGenerateToken(claims, userDetails.getUsername(), String.valueOf(userId), completeRole);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String userId, String role) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setId(userId).setAudience(role).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public boolean validateToken(String token) {
        //final String email = getEmailFromToken(token);
        return !isTokenExpired(token);
    }
}