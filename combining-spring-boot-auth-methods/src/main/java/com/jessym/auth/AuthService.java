package com.jessym.auth;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
public abstract class AuthService {

    private static final String BASIC_PREFIX = "Basic ";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Base64.Decoder B64_DECODER = Base64.getDecoder();

    public abstract Optional<Authentication> authenticate(HttpServletRequest request);

    protected static Optional<Credentials> extractBasicAuthHeader(@NonNull HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (nonNull(authorization)) {
                if (authorization.startsWith(BASIC_PREFIX)) {
                    String encodedCredentials = authorization.substring(BASIC_PREFIX.length());
                    String decodedCredentials = new String(B64_DECODER.decode(encodedCredentials), UTF_8);
                    if (decodedCredentials.contains(":")) {
                        String[] split = decodedCredentials.split(":", 2);
                        Credentials credentials = new Credentials(split[0], split[1]);
                        return Optional.of(credentials);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract basic credentials", e);
            return Optional.empty();
        }
    }

    protected static Optional<String> extractBearerTokenHeader(@NonNull HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (nonNull(authorization)) {
                if (authorization.startsWith(BEARER_PREFIX)) {
                    String token = authorization.substring(BEARER_PREFIX.length()).trim();
                    if (!token.isBlank()) {
                        return Optional.of(token);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract bearer token", e);
            return Optional.empty();
        }
    }

    protected static Authentication createAuthentication(String actor, @NonNull Role... roles) {
        // The difference between `hasAuthority` and `hasRole` is that the latter uses the `ROLE_` prefix
        List<GrantedAuthority> authorities = Stream.of(roles)
                .distinct()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(nonNull(actor) ? actor : "N/A", "N/A", authorities);
    }

    @Data
    protected static class Credentials {

        private final String username;
        @ToString.Exclude
        private final String password;

    }

}
