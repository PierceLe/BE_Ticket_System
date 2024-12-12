package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.user.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.user.*;
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

    // create users api
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }

    // get all users api
    @GetMapping
    public ApiResponse<List<Users>> getAllUsers() {
        return userService.getAllUsers();
    }

    // get specific user api
    @GetMapping("/{id}")
    public Users getUserById(
            @PathVariable @NonNull String id
    ) {
        return userService.getUserById(id);
    }


    // update user api
    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable @NonNull String id,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        return userService.updateUser(id, userUpdateRequest);
    }

    // update user password api
    @PutMapping("/{id}/password")
    public UserResponse userUpdatePassword(
            @PathVariable @NonNull String id,
            @RequestBody UserUpdatePasswordRequest userUpdateRequest
    ) {
        return userService.updateUserPassword(id, userUpdateRequest);
    }

    // update user role
    @PutMapping("/{id}/role")
    public UserResponse userUpdatePassword(
            @PathVariable @NonNull String id,
            @RequestBody UserUpdateRoleRequest userUpdateRequest
    ) {
        return userService.updateUserRole(id, userUpdateRequest);
    }

    // update user unit
    @PutMapping("/{id}/unit")
    public UserResponse userUpdateUnit(
            @PathVariable @NonNull String id,
            @RequestBody UserUpdateUnitRequest request
    ) {
        return userService.updateUserUnit(id, request);
    }


    // update user name
    @PutMapping("{id}/username")
    public UserResponse userUpdateUsername(
            @PathVariable @NonNull String id,
            @RequestBody UserUpdateUsernameRequest request
    ) {
        return userService.updateUserName(id, request);
    }


    // delete users api
    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable @NonNull String id
    ) {
        userService.deleteUser(id);
    }
}
