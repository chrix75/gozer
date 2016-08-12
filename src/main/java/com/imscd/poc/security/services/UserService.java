package com.imscd.poc.security.services;

import com.imscd.poc.authorities.AuthorityManager;
import com.imscd.poc.authorities.FakeAuthorityManager;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotNull;

/**
 * Created by Christian Sperandio on 16/07/2016.
 */
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    private final AuthorityManager authorityManager = new FakeAuthorityManager();

    @Override
    public final User loadUserByUsername(@NotNull String login) throws UsernameNotFoundException {

        String[] parts = login.split("\\\\");

        if (parts.length != 3) { throw new UsernameNotFoundException("Invalid login"); }

        String apiKey = parts[0];
        String application = parts[1];
        String username = parts[2];

        User user = new User(username,
                "",
                authorityManager.authorities(apiKey, application, username));

        return user;
    }
}