package com.company.customeridentificationsystem;


import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

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

    private final String password = "Test12345";

    private final UserRepository userRepository;

    public DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        IntStream.range(0, usernames.size())
                .mapToObj(i -> User.builder()
                                .username(usernames.get(i))
                                .firstName(firstNames.get(i))
                                .lastName(lastNames.get(i))
                                .password(new BCryptPasswordEncoder(11).encode(password))
                                .build())
                .filter(e -> !userRepository.findByUsername(e.getUsername()).isPresent())
                .forEach(userRepository::save);
    }

}
