package com.imscd.poc.authorities;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by Christian Sperandio on 17/07/2016.
 */
public interface AuthorityManager {
    List<GrantedAuthority> authorities(String apiKey, String application, String username);

    boolean exists(String username);
}
