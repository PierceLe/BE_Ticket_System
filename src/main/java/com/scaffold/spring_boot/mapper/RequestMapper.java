package com.scaffold.spring_boot.mapper;

import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.entity.Request;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestResponse toRequestResponse(Request request);
}
