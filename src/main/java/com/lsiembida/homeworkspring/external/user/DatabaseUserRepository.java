package com.lsiembida.homeworkspring.external.user;

import com.lsiembida.homeworkspring.domain.user.User;
import com.lsiembida.homeworkspring.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public void create(User user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
        jpaUserRepository.save(entity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(ent -> new User(ent.getUsername(), ent.getPassword(), ent.getRole()));
    }
}
