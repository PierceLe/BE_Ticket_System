package com.scaffold.ticketSystem.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scaffold.ticketSystem.dto.request.unit.UnitCreationRequest;
import com.scaffold.ticketSystem.dto.response.ApiResponse;
import com.scaffold.ticketSystem.dto.response.UnitCreationResponse;
import com.scaffold.ticketSystem.dto.response.UnitResponse;
import com.scaffold.ticketSystem.dto.response.UserResponse;
import com.scaffold.ticketSystem.entity.Unit;
import com.scaffold.ticketSystem.exception.AppException;
import com.scaffold.ticketSystem.exception.ErrorCode;
import com.scaffold.ticketSystem.mapper.UnitMapper;
import com.scaffold.ticketSystem.mapper.UserMapper;
import com.scaffold.ticketSystem.repository.UnitRepository;
import com.scaffold.ticketSystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final UnitMapper unitMapper;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;

    public UnitCreationResponse createUnit(UnitCreationRequest request) {
        Boolean unitExist = unitRepository.existsByName(request.getName());

        if (unitExist) {
            throw new AppException(ErrorCode.UNIT_EXISTED);
        }
        Unit unit = modelMapper.map(request, Unit.class);
        unitRepository.save(unit);
        return modelMapper.map(unit, UnitCreationResponse.class);
    }

    public ApiResponse<List<UnitResponse>> getAllUnits() {
        List<Unit> units = unitRepository.findAll();
        return ApiResponse.<List<UnitResponse>>builder()
                .result(unitMapper.toUnitResponseList(units))
                .build();
    }

    public UnitResponse getUnitById(Integer id) {
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));

        return unitMapper.toUnitResponse(unit);
    }

    public UnitResponse getUnitByName(String name) {
        Unit unit =
                unitRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.UNIT_NAME_NOT_EXISTED));

        return unitMapper.toUnitResponse(unit);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    public UnitResponse updateUnit(Integer id, UnitCreationRequest request) {
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNIT_ID_NOT_EXISTED));
        if (unitRepository.existsByName(request.getName()) && !unit.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.UNIT_NAME_EXISTED);
        }
        unit.setName(request.getName());
        return unitMapper.toUnitResponse(unitRepository.save(unit));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    public void deleteUnit(Integer id) {
        unitRepository.deleteById(id);
    }

    public List<UserResponse> getUsersInSpecficUnits(Integer unitId) {
        return userMapper.toUserResponseList(userRepository.findByUnitId(unitId));
    }
}
