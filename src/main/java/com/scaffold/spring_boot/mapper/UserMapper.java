package com.scaffold.spring_boot.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.scaffold.spring_boot.dto.request.user.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.user.UserUpdateRequest;
import com.scaffold.spring_boot.dto.request.user.UserUpdateUsernameRequest;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest request);
    // target mapping is user, which means, all request will be mapped to Users
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);

    void updateUserName(@MappingTarget Users user, UserUpdateUsernameRequest request);

    UserResponse toUserResponse(Users user);

    List<UserResponse> toUserResponseList(List<Users> users);
}
