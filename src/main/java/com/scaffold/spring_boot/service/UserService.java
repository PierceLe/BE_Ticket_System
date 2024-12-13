package com.scaffold.spring_boot.service;


import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.user.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.user.*;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Unit;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.enums.Role;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.mapper.UserMapper;
import com.scaffold.spring_boot.repository.UnitRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EMAIL_EXISTED);
        }
        Users user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role;
        try {
            role = Role.valueOf(request.getRole());
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("role not found");
        }
        user.setRole(role.name());
        user.setCreatedAt(LocalDate.now());
        user.setLocked(false);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Users>> getAllUsers() {
        return ApiResponse.<List<Users>>builder()
                .result(userRepository.findAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    public Users getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    @Transactional
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        if (userRepository.existsByEmail(request.getEmail())
                && !user.getEmail().equals(request.getEmail())
        ) {
            throw new AppException(ErrorCode.USER_EMAIL_EXISTED);
        }
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    public UserResponse updateUserPassword(String id, UserUpdatePasswordRequest request) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        if (passwordEncoder.matches(request.getOldPassword(), users.getPassword())) {
            users.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        return userMapper.toUserResponse(userRepository.save(users));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    @Transactional
    public UserResponse updateUserRole(String id, UserUpdateRoleRequest request) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        try {
            Role roles = Role.valueOf(request.getRole());
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("role not found");
        }
        users.setRole(request.getRole());
        return userMapper.toUserResponse(userRepository.save(users));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserUnit(String id, UserUpdateUnitRequest unit) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if (!unitRepository.existsById(unit.getUnitId())) {
            throw new AppException(ErrorCode.UNIT_ID_NOT_EXISTED);
        }
        users.setUnitId(unit.getUnitId());
        return userMapper.toUserResponse(userRepository.save(users));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    public UserResponse updateUserName(String id, UserUpdateUsernameRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        if (userRepository.existsByUsername(request.getUsername())
                && !user.getUsername().equals(request.getUsername())
        ) {
            throw new AppException(ErrorCode.USER_NAME_EXISTED);
        }
        userMapper.updateUserName(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String id = context.getAuthentication().getName();

        Users user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED)
                );
        return userMapper.toUserResponse(user);
    }

    public UnitResponse getMyUnit() {
        var context = SecurityContextHolder.getContext();
        String id = context.getAuthentication().getName();

        Users user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER__NOT_EXISTED)
        );
        Integer unitId = user.getUnitId();
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));
        return modelMapper.map(unit, UnitResponse.class);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    public List<UserResponse> userSearchFilter(
            String username,
            Integer unitId,
            String role,
            String email,
            String fullName,
            LocalDate dob
    ) {
        List<Users> filteredUsers = userRepository.findUsersByFilters(
                username,
                unitId,
                role,
                email,
                fullName,
                dob != null ? LocalDate.parse(dob.toString()) : null
        );

        return filteredUsers.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse lockUser(String id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));

        if (user.getLocked()) {
            throw new AppException(ErrorCode.USER_HAS_BEEN_LOCKED);
        }
        user.setLocked(true);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse unlockUser(String id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));

        if (!user.getLocked()) {
            throw new AppException(ErrorCode.USER_STILL_ACTIVE);
        }
        user.setLocked(false);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
