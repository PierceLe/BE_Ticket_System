package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.entity.Unit;
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

}
