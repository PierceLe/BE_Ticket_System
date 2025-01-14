package com.scaffold.ticketSystem.mapper;

import org.mapstruct.Mapper;

import com.scaffold.ticketSystem.dto.response.RequestResponse;
import com.scaffold.ticketSystem.entity.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestResponse toRequestResponse(Request request);
}
