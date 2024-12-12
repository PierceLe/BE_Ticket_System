package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.unit.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.entity.Unit;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.mapper.UnitMapper;
import com.scaffold.spring_boot.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
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

    public UnitResponse updateUnit(Integer id, UnitCreationRequest request) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));
        if (unitRepository.existsByName(request.getName())
                && !unit.getName().equals(request.getName())
        ) {
            throw new AppException(ErrorCode.UNIT_NAME_EXISTED);
        }
        unit.setName(request.getName());
        return unitMapper.toUnitResponse(unitRepository.save(unit));
    }

    public void deleteUnit(Integer id) {
        unitRepository.deleteById(id);
    }
}
