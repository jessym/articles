package com.jessym.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
class AuthServiceJwt extends AuthService {

    private final JWTVerifier jwtVerifier;

    AuthServiceJwt(@Value("${auth.jwt.hmacKey}") String hmacKey) {
        Algorithm algo = Algorithm.HMAC256(hmacKey.getBytes(UTF_8));
        this.jwtVerifier = JWT.require(algo).build();
    }

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::verify);
    }

    private Optional<Authentication> verify(String token) {
        try {
            DecodedJWT jwt = this.jwtVerifier.verify(token);
            String issuer = jwt.getIssuer();
            Authentication authentication = createAuthentication(issuer, Role.SYSTEM);
            return Optional.of(authentication);
        } catch (JWTDecodeException e) {
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to verify JWT token", e);
            return Optional.empty();
        }
    }

}
