package com.pranav.ticketing.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
@Service
public class JwtService {
  private final Key key;
  private final String issuer;
  private final long accessTtlMinutes;
  private final long refreshTtlDays;
  public JwtService(
    @Value("${jwt.secret}") String secret,
    @Value("${jwt.issuer}") String issuer,
    @Value("${jwt.accessTtlMinutes}") long accessTtlMinutes,
    @Value("${jwt.refreshTtlDays}") long refreshTtlDays
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.issuer = issuer; this.accessTtlMinutes = accessTtlMinutes; this.refreshTtlDays = refreshTtlDays;
  }
  public String createToken(String sub, Map<String,Object> claims, boolean refresh) {
    long ttl = refresh ? refreshTtlDays * 24 * 60 : accessTtlMinutes;
    Instant now = Instant.now();
    return Jwts.builder()
      .setIssuer(issuer)
      .setSubject(sub)
      .addClaims(claims)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(now.plusSeconds(ttl*60)))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }
  public Jws<Claims> parse(String jwt) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
  }
}
