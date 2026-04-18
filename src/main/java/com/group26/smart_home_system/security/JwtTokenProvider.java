package com.group26.smart_home_system.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.issuer}")
  private String issuer;

  @Value("${jwt.expiration}")
  private long expiration;

  public String generateToken(Long userId, String role) {
    try {
      JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(userId.toString())
          .claim("scope", role)
          .issuer(issuer)
          .issueTime(new Date())
          .expirationTime(new Date(System.currentTimeMillis() + expiration))
          .jwtID(generateJti())
          .build();

      JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(claimsSet.toJSONObject()));
      jwsObject.sign(new MACSigner(secret.getBytes()));

      return jwsObject.serialize();
    } catch (JOSEException exception) {
      throw new RuntimeException(exception);
    }
  }

  public String refreshToken(String token) {
    try {
      SignedJWT oldJwt = SignedJWT.parse(token);

      String userId = oldJwt.getJWTClaimsSet().getSubject();
      String role = oldJwt.getJWTClaimsSet().getStringClaim("role");

      return generateToken(Long.valueOf(userId), role);
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public Date extractExpiration(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      return signedJWT.getJWTClaimsSet().getExpirationTime();
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  private String generateJti() {
    return java.util.UUID.randomUUID().toString();
  }

}
