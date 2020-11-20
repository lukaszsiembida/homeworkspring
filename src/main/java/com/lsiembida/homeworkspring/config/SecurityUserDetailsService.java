package com.lsiembida.homeworkspring.config;

import com.lsiembida.homeworkspring.domain.user.User;
import com.lsiembida.homeworkspring.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> mapToUserDetails(user))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UserDetails mapToUserDetails(User user) {
        //JESLI DODAJESZ ROLE DO UZYTKOWNIKA TO MUSIMY JA POPRZEDZIC PREFIXEM ROLE_
        List<GrantedAuthority> authorities =
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }
}
