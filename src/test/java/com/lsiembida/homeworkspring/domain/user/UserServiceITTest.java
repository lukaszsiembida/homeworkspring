package com.lsiembida.homeworkspring.domain.user;


import com.lsiembida.homeworkspring.external.user.JpaUserRepository;
import com.lsiembida.homeworkspring.external.user.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class UserServiceITTest {
    @Autowired
    private UserService userService;
    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldCreateUser() {
        //given
        String password = "StrongPassword1!";
        User user = new User("login", password, "admin");
        //when
        userService.register(user);
        //then
        List<UserEntity> all = userRepository.findAll();
        Assertions.assertEquals(1, all.size());
        UserEntity persistedUser = all.get(0);
        Assertions.assertEquals("login", persistedUser.getUsername());
        Assertions.assertEquals("admin", persistedUser.getRole());
        Assertions.assertTrue(passwordEncoder.matches(password, persistedUser.getPassword()));
    }
}
