package com.ureca.sole_paradise.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    String apiKey = "6424817121242405";
    String secretKey = "Qxc7mtPG7i3Rp0s2g4t9ftrE90QkD1jB32mmYIKQIaYyAdjAYFLD2Q9Ff7aA4KLSa7abVuXcut47ZTQ9";

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }

}
