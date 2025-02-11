package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserInfoDto;
import ru.otus.hw.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/user")
    public List<UserInfoDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/api/v1/user/{id}")
    public UserDto getUser(@PathVariable long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/api/v1/user/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
    }

    @PostMapping("/api/v1/user")
    public UserDto createUser(@Valid @RequestBody UserDto user) {
        return userService.create(user);
    }

    @PutMapping("/api/v1/user")
    public UserDto updateUser(@Valid @RequestBody UserDto user) {
        return userService.update(user);
    }
}
