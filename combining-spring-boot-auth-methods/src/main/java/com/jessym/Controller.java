package com.jessym;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Map.entry;

@RestController
class Controller {

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    Map<String, String> user(Authentication authentication) {
        return Map.ofEntries(
                entry("endpoint", "USER"),
                entry("actor", authentication.getName())
        );
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    Map<String, String> admin(Authentication authentication) {
        return Map.ofEntries(
                entry("endpoint", "ADMIN"),
                entry("actor", authentication.getName())
        );
    }

    @GetMapping("/system")
    @PreAuthorize("hasRole('SYSTEM')")
    Map<String, String> system(Authentication authentication) {
        return Map.ofEntries(
                entry("endpoint", "SYSTEM"),
                entry("actor", authentication.getName())
        );
    }

}
