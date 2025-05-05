package kg.alatoo.midterm_project.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import kg.alatoo.midterm_project.entity.User;
import kg.alatoo.midterm_project.entity.oauth2.UserPrincipal;
import kg.alatoo.midterm_project.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
  @Value("${jwt.secret-key}")
  private String secretKey;
  private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000;
  private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;
  private final RefreshTokenService refreshTokenService;

  public String generateAccessToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    if (userDetails instanceof User customUserDetails) {
      claims.put("email", customUserDetails.getEmail());
      claims.put("role", "ROLE_" + customUserDetails.getUserRole().getName());
    }
    else if (userDetails instanceof UserPrincipal userPrincipal) {
      claims.put("email", userPrincipal.getEmail());
      claims.put("role", "ROLE_" + userPrincipal.getRole());
    }
    return buildToken(claims, userDetails, ACCESS_TOKEN_EXPIRATION);
  }

  public String generateRefreshToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    User user;
    if (userDetails instanceof User customUserDetails) {
      user = customUserDetails;
      claims.put("email", user.getEmail());
      claims.put("role", user.getUserRole().getName());
    } else {
      throw new RuntimeException("UserDetails is not an instance of User");
    }

    String token = buildToken(claims, userDetails, REFRESH_TOKEN_EXPIRATION);
    refreshTokenService.saveToken(token, user);

    return token;

  }
  private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {

    JwtBuilder jwtBuilder = Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256);

    return jwtBuilder.compact();
  }
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}