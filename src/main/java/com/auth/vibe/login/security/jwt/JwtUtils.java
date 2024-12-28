package com.auth.vibe.login.security.jwt;


import com.auth.vibe.login.model.UserModel;
import com.auth.vibe.login.security.payload.SignInDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public  String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public  boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
//            return true;
            Claims claims = Jwts.parser()
                    .setSigningKey((SecretKey) key())
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();

            // Check if roles are present
            if (claims.containsKey("roles")) {
              return true;
            }

            return false; // Roles do not match or missing
        } catch (MalformedJwtException e) {
            System.out.println("JWT token is malformed: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public String generateTokenWithContent(String username, String content, int expiryMinutes) {
        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + expiryMinutes * 60 * 1000); // Set expiration time
        return Jwts.builder()
                .subject(username)
                .claim("action", content)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key())
                .compact();
    }

    public Collection<? extends GrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getBody();

        ArrayList roles = (ArrayList) claims.get("roles");
        HashMap hashMap = (HashMap) roles.get(0);
        Collection<GrantedAuthority> collection = (Collection<GrantedAuthority>) roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());


//        Collection< GrantedAuthority> authorities = roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.toString()))
//                .collect(Collectors.toList());
        return collection;
    }


    public boolean validateJwtTokenWithAction(String token) {
        try {
            System.out.println("Validate");
           Claims claims = Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getBody();
            String action = claims.get("action", String.class);
            if(Objects.equals(action, "RESET_PASSWORD"))
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("JWT token is malformed: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
