package com.scaffold.spring_boot.service;


import com.scaffold.spring_boot.dto.request.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.UserUpdatePasswordRequest;
import com.scaffold.spring_boot.dto.request.UserUpdateRequest;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Unit;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.enums.Role;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.mapper.UserMapper;
import com.scaffold.spring_boot.repository.UnitRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final UserMapper userMapper;

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
        try {
            Role role = Role.valueOf(request.getRole());
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("role not found");
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }
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

    public UserResponse updatePassword(String id, UserUpdatePasswordRequest request) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(request.getOldPassword(), users.getPassword())) {
            users.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        return userMapper.toUserResponse(userRepository.save(users));
    }

    @Transactional
    public UserResponse updateRole(String id, String role) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        try {
            Role roles = Role.valueOf(role);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("role not found");
        }
        users.setRole(role);
        return userMapper.toUserResponse(userRepository.save(users));
    }

    public UserResponse updateUnit(String id, Integer unit) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if (!unitRepository.existsById(unit)) {
            throw new AppException(ErrorCode.UNIT_ID_NOT_EXISTED);
        }
        users.setUnitId(unit);
        return userMapper.toUserResponse(userRepository.save(users));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
