package com.scaffold.ticketSystem.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.scaffold.ticketSystem.dto.request.user.*;
import com.scaffold.ticketSystem.dto.request.user.UserCreationRequest;
import com.scaffold.ticketSystem.dto.response.ApiResponse;
import com.scaffold.ticketSystem.dto.response.UnitResponse;
import com.scaffold.ticketSystem.dto.response.UserResponse;
import com.scaffold.ticketSystem.service.UserService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    // get user api
    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    // get specific user api
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable @NonNull String id) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.getUserById(id))
                .build();
    }

    // update user api
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable @NonNull String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.updateUser(id, userUpdateRequest))
                .build();
    }

    // update user password api
    @PutMapping("/{id}/password")
    public ApiResponse<UserResponse> userUpdatePassword(
            @PathVariable @NonNull String id, @RequestBody UserUpdatePasswordRequest userUpdateRequest) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.updateUserPassword(id, userUpdateRequest))
                .build();
    }

    // update user role
    @PutMapping("/{id}/role")
    public ApiResponse<UserResponse> userUpdateRole(
            @PathVariable @NonNull String id, @RequestBody UserUpdateRoleRequest userUpdateRequest) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.updateUserRole(id, userUpdateRequest))
                .build();
    }

    // update user unit
    @PutMapping("/{id}/unit")
    public ApiResponse<UserResponse> userUpdateUnit(
            @PathVariable @NonNull String id, @RequestBody UserUpdateUnitRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.updateUserUnit(id, request))
                .build();
    }

    // update user name
    @PutMapping("{id}/username")
    public UserResponse userUpdateUsername(
            @PathVariable @NonNull String id, @RequestBody UserUpdateUsernameRequest request) {
        return userService.updateUserName(id, request);
    }

    // delete users api
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @NonNull String id) {
        userService.deleteUser(id);
    }

    // get users with filter
    @GetMapping
    public ApiResponse<List<UserResponse>> getSearchFilter(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "unitId", required = false) Integer unitId,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "dob", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dob) {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.userSearchFilter(username, unitId, role, email, fullName, dob));
        return apiResponse;
    }

    // lock user account
    @PostMapping("/{id}/lock")
    public ApiResponse<UserResponse> lockUser(@PathVariable @NotNull String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.lockUser(id))
                .build();
    }

    // unlock user account
    @PostMapping("/{id}/unlock")
    public ApiResponse<UserResponse> unlockUser(@PathVariable @NotNull String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.unlockUser(id))
                .build();
    }

    // get user's unit
    @GetMapping("/myUnit")
    public ApiResponse<UnitResponse> getMyUnit() {
        return ApiResponse.<UnitResponse>builder()
                .result(userService.getMyUnit())
                .build();
    }

    // update user's avatar
    @PutMapping("{id}/avatar")
    public ApiResponse<UserResponse> userUpdateUserAvatar(
            @PathVariable @NonNull String id, @RequestParam("file") @NonNull MultipartFile file) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.updateUserAvatar(id, file))
                .build();
    }

    // delete user's avatar
    @DeleteMapping("{id}/avatar")
    public ApiResponse<UserResponse> userDeleteAvatar(@PathVariable @NonNull String id) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.deleteUserAvatar(id))
                .build();
    }
}
