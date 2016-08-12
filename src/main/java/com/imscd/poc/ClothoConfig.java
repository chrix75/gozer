package com.imscd.poc;

import com.imscd.poc.authorities.AuthorityManager;
import com.imscd.poc.authorities.FakeAuthorityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Christian Sperandio on 17/07/2016.
 */
@Configuration
public class ClothoConfig {
    @Bean
    public AuthorityManager authorityManager() {
        return new FakeAuthorityManager();
    }
}
