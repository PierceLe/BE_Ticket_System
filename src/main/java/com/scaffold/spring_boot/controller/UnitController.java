package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.unit.UnitCreationRequest;
import com.scaffold.spring_boot.dto.response.UnitCreationResponse;
import com.scaffold.spring_boot.dto.response.UnitResponse;
import com.scaffold.spring_boot.service.UnitService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @PostMapping
    public UnitCreationResponse createUnit(
            @RequestBody UnitCreationRequest request
    ) {
        return unitService.createUnit(request);
    }

    @GetMapping
    public ApiResponse<List<UnitResponse>> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("{id}")
    public UnitResponse getUnitById(@PathVariable @NonNull Integer id) {
        return unitService.getUnitById(id);
    }

    @GetMapping("{name}/name")
    public UnitResponse getUnitByName(@PathVariable @NonNull String  name) {
       return unitService.getUnitByName(name);
    }

    @PutMapping("{id}")
    public UnitResponse updateUnit(
            @PathVariable @NonNull Integer id,
            @RequestBody UnitCreationRequest request
    ) {
        return unitService.updateUnit(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteUnit(@PathVariable @NonNull Integer id) {
        unitService.deleteUnit(id);
    }
}
