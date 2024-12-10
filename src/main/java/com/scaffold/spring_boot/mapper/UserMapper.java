package com.scaffold.spring_boot.mapper;

import com.scaffold.spring_boot.dto.request.UserCreationRequest;
import com.scaffold.spring_boot.dto.request.UserUpdateRequest;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest request);
    // target mapping is user, which means, all request will be mapped to Users
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);

    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(Users user);
}
