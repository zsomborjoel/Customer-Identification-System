package com.company.customeridentificationsystem;


import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final List<String> usernames = asList(
            "ada.lovelace@nix.io",
            "alan.turing@nix.io",
            "dennis.ritchie@nix.io"
    );
    private final List<String> firstNames = asList(
            "Ada",
            "Alan",
            "Dennis"
    );
    private final List<String> lastNames = asList(
            "Lovelace",
            "Turing",
            "Ritchie"
    );

    private final String password = "Test12345_";

    private final UserRepository userRepository;

    public DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        for (int i = 0; i < usernames.size(); ++i) {
            User user = User.builder()
                    .username(usernames.get(i))
                    .firstName(firstNames.get(i))
                    .lastName(lastNames.get(i))
                    .password(password)
                    .build();

            userRepository.save(user);
        }
    }

}
