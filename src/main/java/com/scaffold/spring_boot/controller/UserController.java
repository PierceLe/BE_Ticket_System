package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.user.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.user.*;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    // create users api
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }


    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
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

    @GetMapping
    public ApiResponse<List<UserResponse>> getSearchFilter(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "unitId", required = false) Integer unitId,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "dob", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob
    ) {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.userSearchFilter(username, unitId, role, email, fullName, dob));
        return apiResponse;
    }

    @PostMapping("/{id}/lock")
    public ApiResponse<UserResponse> lockUser(
        @PathVariable @NotNull String id
    ) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.lockUser(id))
                .build();
    }

    @PostMapping("/{id}/unlock")
    public ApiResponse<UserResponse> unlockUser(
            @PathVariable @NotNull String id
    ) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.unlockUser(id))
                .build();
    }

}
