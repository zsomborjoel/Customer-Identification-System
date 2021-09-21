package com.company.customeridentificationsystem.model.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "picture.validity.system")
public class PictureValiditySystemProperties {

    private String url;

}
