package com.company.customeridentificationsystem.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private Map<String, String> testUsers = new HashMap<>();

}
