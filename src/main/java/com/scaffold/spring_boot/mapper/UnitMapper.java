package com.scaffold.spring_boot.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.scaffold.spring_boot.dto.request.unit.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    Unit toUnit(UnitCreationRequest unitCreationRequest);

    UnitCreationResponse toUnitCreationResponse(Unit unit);

    UnitResponse toUnitResponse(Unit unit);

    List<UnitResponse> toUnitResponseList(List<Unit> units);
}
