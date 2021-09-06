package org.churchsource.churchmusicteam.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.impl.DefaultClock;

@Component
public class JwtTokenService implements Serializable {

  static final String CLAIM_KEY_USERNAME = "sub";
  static final String CLAIM_KEY_CREATED = "iat";
  private static final long serialVersionUID = -3301605591108950415L;
  public static final String JWT_TOKEN_REASON = "reason";
  public static final String JWT_TOKEN_PRIVILEGES = "privileges";

  public static final String JWT_TOKEN_REASON_PASSWORD_CHANGE = "passwordChange";
  private Clock clock = DefaultClock.INSTANCE;

  @Value("${jwt.signing.key.secret}")
  private String secret;

  @Value("${jwt.token.expiration.in.seconds}")
  private Long expiration;

  @Value("${jwt.passwordchangetoken.expiration.in.seconds}")
  private Long passwordChangeTokenExpiration;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public String getPrivilegesFromToken(String token) {
    return (String)Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get(JWT_TOKEN_PRIVILEGES);
  }

  public String getReasonFromToken(String token) {
    return (String)Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get(JWT_TOKEN_REASON);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }


  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    try {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    } catch(ExpiredJwtException e) {
        return true;
    }
  }

  private Boolean ignoreTokenExpiration(String token) {
    // here you specify tokens, for that the expiration is ignored
    return false;
  }

  public Boolean isTokenValid(String token) {
    try {
        final String username = getUsernameFromToken(token);
        return (!isTokenExpired(token));
    } catch (ExpiredJwtException e) {
        return false;
    }
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 1000);
  }
}

