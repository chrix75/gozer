package com.imscd.poc.controllers;

import com.imscd.poc.authorities.AuthorityManager;
import com.imscd.poc.authorities.ResponseAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Christian Sperandio on 17/07/2016.
 */
@RestController
@RequestMapping("/permissions")
public class AuthoritiesController {

    @Autowired
    private AuthorityManager authorityManager;

    @RequestMapping(value = "/company/{apiKey}/application/{application}/user/{login}", method = RequestMethod.GET)
    public ResponseAuthorities authorities(@PathVariable String apiKey,
                                           @PathVariable String application,
                                           @PathVariable String login) {


        ResponseAuthorities responseAuthorities = new ResponseAuthorities();
        responseAuthorities.setApikey(apiKey);
        responseAuthorities.setLogin(login);

        List<String> authorities = authorityManager.authorities(apiKey, application, login)
                .stream().map(a -> a.getAuthority()).collect(Collectors.toList());

        responseAuthorities.setAuthorities(authorities);

        return responseAuthorities;
    }

}
