package com.imscd.poc.security;

import com.imscd.poc.exceptions.JWTTokenException;
import com.imscd.poc.security.services.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Christian Sperandio on 16/07/2016.
 */
public final class TokenHandler {

    private final String marker = "Bearer ";
    private final UserService userService;
    private final JWTManager jwtManager;

    public TokenHandler(UserService userService, JWTManager jwtManager) {
        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    public Optional<User> parseUserFromHeader(@NotNull String header, @NotNull String application) {

        Optional<String> login = buildCompleteLogin(header, application);

        if (login.isPresent()) {
            return Optional.of(userService.loadUserByUsername(login.get()));
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> buildCompleteLogin(String header, String application) {
        if (!header.startsWith(marker) || application.isEmpty()) {
            return Optional.empty();
        }

        String token = header.substring(marker.length());
        Map<String, Object> claims = null;
        try {
            claims = jwtManager.getClaims(token);
        } catch (JWTTokenException e) {
            throw new UsernameNotFoundException("Invalid token", e);
        }

        String apiKey = (String) claims.get("aud");
        String login = (String) claims.get("sub");

        if (apiKey == null || login == null) { return Optional.empty(); }

        if (apiKey.isEmpty() || login.isEmpty()) { return Optional.empty(); }

        return Optional.of(String.format("%s\\%s\\%s", apiKey, application, login));
    }
}