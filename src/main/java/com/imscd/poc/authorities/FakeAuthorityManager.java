package com.imscd.poc.authorities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christian Sperandio on 17/07/2016.
 */
public class FakeAuthorityManager implements AuthorityManager {
    private Map<CompanyUser, List<GrantedAuthority>> authorities;

    public FakeAuthorityManager() {
        authorities = new HashMap<>();
        List<GrantedAuthority> currentAuthorities = new ArrayList<>();

        // IMS CD Services
        currentAuthorities.add(new SimpleGrantedAuthority("ROLE_IMSCD_SERVICE"));
        authorities.put(new CompanyUser("a5009daa-803b-455f-9b8f-9c3bf97fc989", "clotho", "gilles"), currentAuthorities);

        // an admin
        currentAuthorities = new ArrayList<>();
        currentAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.put(new CompanyUser("a5009daa-803b-455f-9b8f-9c3bf97fc989", "mabo", "batman"), currentAuthorities);
        authorities.put(new CompanyUser("a5009daa-803b-455f-9b8f-9c3bf97fc989", "ddi", "batman"), currentAuthorities);

        // an user
        currentAuthorities = new ArrayList<>();
        currentAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        currentAuthorities.add(new SimpleGrantedAuthority("ROLE_EXPERT"));
        authorities.put(new CompanyUser("4b21f7db-b0a1-47a3-9007-a0547c7104cd", "ddi", "peter_parker"), currentAuthorities);
    }

    @Override
    public List<GrantedAuthority> authorities(String apiKey, String application, String username) {
        CompanyUser companyUser = new CompanyUser(apiKey, application, username);
        List<GrantedAuthority> found = authorities.get(companyUser);
        if (found == null) {
            return new ArrayList<>();
        }

        return found;
    }

    @Override
    public boolean exists(@NotNull String username) {
        String[] parts = username.split("\\\\");
        if (parts.length != 2) {
            return false;
        }

        String login = parts[1];
        String apiKey = parts[0];

        for (CompanyUser cu : authorities.keySet()) {
            if (cu.getLogin().equals(login) && cu.getCompanyApiKey().equals(apiKey)) { return true; }
        }

        return false;
    }
}
