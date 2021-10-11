package com.company.customeridentificationsystem.security;

import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.model.dao.UserPrincipal;
import com.company.customeridentificationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
           throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user.get());
    }

}
