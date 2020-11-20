package com.lsiembida.homeworkspring.domain.user;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private String role;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

}
