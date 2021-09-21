package com.company.customeridentificationsystem.config;

import com.company.customeridentificationsystem.model.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder authentication) {
        if (securityProperties.getTestUsers().isEmpty()) {
            throw new ExceptionInInitializerError("No user created");
        }

        securityProperties.getTestUsers().forEach((key, value) -> {
            try {
                authentication.inMemoryAuthentication()
                        .withUser(key)
                        .password(passwordEncoder().encode(value))
                        .authorities(ROLE_USER);
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
