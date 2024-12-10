package com.ing.demo.store_management.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Getter
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfigProperties {
    private final List<String> ignoredPaths = new ArrayList<>();
}
