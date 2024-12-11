package com.scaffold.spring_boot.service;


import com.scaffold.spring_boot.dto.request.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.UserUpdatePasswordRequest;
import com.scaffold.spring_boot.dto.request.UserUpdateRequest;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.enums.Role;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EMAIL_EXISTED);
        }
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setDob(request.getDob());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName(
// ------- same with this line----------------------------
        Users user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

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

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
