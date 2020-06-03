package com.jessym.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
class AuthServiceRedis extends AuthService {

    private final RedisTemplate<String, String> redis;

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::lookup);
    }

    private Optional<Authentication> lookup(String token) {
        try {
            String userId = this.redis.opsForValue().get(token);
            if (nonNull(userId)) {
                Authentication authentication = createAuthentication(userId, Role.USER);
                return Optional.of(authentication);
            }
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to look up Redis token", e);
            return Optional.empty();
        }
    }

}
