package com.scaffold.ticketSystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.scaffold.ticketSystem.dto.request.user.UserCreationRequest;
import com.scaffold.ticketSystem.dto.request.user.UserUpdateRequest;
import com.scaffold.ticketSystem.dto.request.user.UserUpdateUsernameRequest;
import com.scaffold.ticketSystem.dto.response.UserResponse;
import com.scaffold.ticketSystem.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest request);
    // target mapping is user, which means, all request will be mapped to Users
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);

    void updateUserName(@MappingTarget Users user, UserUpdateUsernameRequest request);

    UserResponse toUserResponse(Users user);

    List<UserResponse> toUserResponseList(List<Users> users);
}
