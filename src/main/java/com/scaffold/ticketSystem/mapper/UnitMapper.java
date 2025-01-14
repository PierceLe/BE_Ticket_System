package com.scaffold.ticketSystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.scaffold.ticketSystem.dto.request.unit.UnitCreationRequest;
import com.scaffold.ticketSystem.dto.response.UnitCreationResponse;
import com.scaffold.ticketSystem.dto.response.UnitResponse;
import com.scaffold.ticketSystem.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    Unit toUnit(UnitCreationRequest unitCreationRequest);

    UnitCreationResponse toUnitCreationResponse(Unit unit);

    UnitResponse toUnitResponse(Unit unit);

    List<UnitResponse> toUnitResponseList(List<Unit> units);
}
