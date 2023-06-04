package com.example.demo.utils;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.example.demo.Details.UserDetails;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {

  @Value("${bezkoder.app.jwtSecret}")
  private String jwtSecret;

  @Value("${bezkoder.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${bezkoder.app.jwtCookieName}")
  private String jwtCookie;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserDetails userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getEmail());
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/")
        .maxAge(jwtExpirationMs/1000).httpOnly(true).build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
        .path("/").build();
    return cookie;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
        .getBody().getSubject();
  }

  public int validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return 0;
    } catch (ExpiredJwtException e) {
      return 1;
    } catch(Exception e) {
      return 2;
    }
  }
  
  public String generateTokenFromUsername(String username) {   
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateTokenFromUserId(Long userId) {
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public Long getUserIdFromJwtToken(String token) {
    String userIdAsString = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
        .getBody().getSubject();
    try {
      return Long.parseLong(userIdAsString);
    } catch(NumberFormatException e) {
      return null;
    }
  }
}
