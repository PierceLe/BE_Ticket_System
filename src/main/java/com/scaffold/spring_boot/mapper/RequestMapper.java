package com.scaffold.spring_boot.mapper;

import org.mapstruct.Mapper;

import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.entity.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestResponse toRequestResponse(Request request);
}
