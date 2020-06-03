package com.jessym.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
class AuthServiceBasic extends AuthService {

    private static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder();

    private final String username;
    private final String password;

    AuthServiceBasic(@Value("${auth.basic.username}") String username, @Value("${auth.basic.password}") String password) {
        this.username = username;
        this.password = BCRYPT.encode(password);
    }

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<Authentication> check(Credentials credentials) {
        try {
            if (credentials.getUsername().equals(this.username)) {
                if (BCRYPT.matches(credentials.getPassword(), this.password)) {
                    Authentication authentication = createAuthentication(credentials.getUsername(), Role.ADMIN);
                    return Optional.of(authentication);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to check Basic Auth credentials", e);
            return Optional.empty();
        }
    }

}
