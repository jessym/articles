package com.jessym;

import com.jessym.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final List<AuthService> authServices;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Auth filter
                .addFilterAt(this::authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Auth on all endpoints
                .authorizeRequests(conf -> {
                    conf.anyRequest().authenticated();
                })
                // Disable "JSESSIONID" cookies
                .sessionManagement(conf -> {
                    conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // Exception handling
                .exceptionHandling(conf -> {
                    conf.authenticationEntryPoint(this::authenticationFailedHandler);
                });
    }

    private void authenticationFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<Authentication> authentication = this.authenticate((HttpServletRequest) request);
        authentication.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> authenticate(HttpServletRequest request) {
        for (AuthService authService : this.authServices) {
            Optional<Authentication> authentication = authService.authenticate(request);
            if (authentication.isPresent()) {
                return authentication;
            }
        }
        return Optional.empty();
    }

    private void authenticationFailedHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        // Trigger the browser to prompt for Basic Auth
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
