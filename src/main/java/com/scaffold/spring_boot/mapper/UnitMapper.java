package com.scaffold.spring_boot.mapper;

import com.scaffold.spring_boot.dto.request.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.entity.Unit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    Unit toUnit(UnitCreationRequest unitCreationRequest);
    UnitCreationResponse toUnitCreationResponse(Unit unit);
    List<UnitResponse> toUnitResponseList(List<Unit> units);
}
