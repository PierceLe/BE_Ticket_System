package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.UserUpdateRequest;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<Users>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUserById(
            @PathVariable @NonNull String id
    ) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable @NonNull String id,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable @NonNull String id
    ) {
        userService.deleteUser(id);
    }
}
