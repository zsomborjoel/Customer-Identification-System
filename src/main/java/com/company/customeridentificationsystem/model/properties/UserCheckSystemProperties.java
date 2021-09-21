package com.company.customeridentificationsystem.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "user.check.system")
public class UserCheckSystemProperties {

    private String url;

}
