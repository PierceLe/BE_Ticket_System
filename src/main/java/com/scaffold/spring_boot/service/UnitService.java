package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.unit.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.entity.Unit;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.mapper.UnitMapper;
import com.scaffold.spring_boot.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public UnitCreationResponse createUnit(UnitCreationRequest request) {
        Unit unit = unitMapper.toUnit(request);
        return unitMapper.toUnitCreationResponse(unitRepository.save(unit));
    }

    public ApiResponse<List<UnitResponse>> getAllUnits() {
        List<Unit> units = unitRepository.findAll();
        return ApiResponse.<List<UnitResponse>>builder()
                .result(unitMapper.toUnitResponseList(units))
                .build();
    }

    public UnitResponse getUnitById(Integer id) {
        Unit unit = unitRepository.findById(id)
        .orElseThrow(() ->  new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));

        return unitMapper.toUnitResponse(unit);
    }

    public UnitResponse getUnitByName(String name) {
        Unit unit = unitRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NAME_NOT_EXISTED));

        return unitMapper.toUnitResponse(unit);

    }

}
