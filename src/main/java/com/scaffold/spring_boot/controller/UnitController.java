package com.scaffold.spring_boot.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.scaffold.spring_boot.dto.request.unit.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.ApiResponse;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.service.UnitService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UnitCreationResponse createUnit(@RequestBody UnitCreationRequest request) {
        return unitService.createUnit(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping
    public ApiResponse<List<UnitResponse>> getAllUnits() {
        return unitService.getAllUnits();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping("{id}")
    public UnitResponse getUnitById(@PathVariable @NonNull Integer id) {
        return unitService.getUnitById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping("{name}/name")
    public UnitResponse getUnitByName(@PathVariable @NonNull String name) {
        return unitService.getUnitByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public UnitResponse updateUnit(@PathVariable @NonNull Integer id, @RequestBody UnitCreationRequest request) {
        return unitService.updateUnit(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteUnit(@PathVariable @NonNull Integer id) {
        unitService.deleteUnit(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping("/{id}/users")
    public ApiResponse<List<UserResponse>> getUsersInSpecficUnits(@PathVariable @NonNull Integer id) {
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .result(unitService.getUsersInSpecficUnits(id))
                .build();
    }
}
