package org.arjunaoverdrive.tasktracker.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.tasktracker.dao.UserRepository;
import org.arjunaoverdrive.tasktracker.model.User;
import org.arjunaoverdrive.tasktracker.service.UserService;
import org.arjunaoverdrive.tasktracker.util.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Set<User>> findAllByIds(Set<String> ids){
        return userRepository.findAllById(ids).collect(Collectors.toSet());
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return findById(user.getId())
                .flatMap(userToUpdate -> {
                    BeanUtils.copyNonNullValues(user, userToUpdate);
                    return userRepository.save(userToUpdate);
                });
    }

    @Override
    public Mono<Void> deleteUserById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
