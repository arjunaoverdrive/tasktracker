package org.arjunaoverdrive.tasktracker.web.controller;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.tasktracker.mapper.UserMapper;
import org.arjunaoverdrive.tasktracker.service.UserService;
import org.arjunaoverdrive.tasktracker.web.dto.request.UserRequest;
import org.arjunaoverdrive.tasktracker.web.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping
    public Flux<UserResponse> getAllUsers(){
        return userService.findAllUsers().map(userMapper::toUserResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable String id){
        return userService.findById(id)
                .map(userMapper::toUserResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest request){
        return userService.createUser(userMapper.toUser(request))
                .map(userMapper::toUserResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable String id,
                                                         @RequestBody UserRequest request){
        return userService.updateUser(userMapper.toUser(id, request))
                .map(userMapper::toUserResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id){
        return userService.deleteUserById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
