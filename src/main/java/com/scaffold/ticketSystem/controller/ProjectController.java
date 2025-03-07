package com.scaffold.ticketSystem.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.scaffold.ticketSystem.dto.request.project.ProjectCreationRequest;
import com.scaffold.ticketSystem.dto.response.ApiResponse;
import com.scaffold.ticketSystem.dto.response.PageResponse;
import com.scaffold.ticketSystem.dto.response.ProjectResponse;
import com.scaffold.ticketSystem.service.ProjectService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProjectResponse createProject(@RequestBody ProjectCreationRequest request) {
        return projectService.createProject(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping
    public ApiResponse<PageResponse<ProjectResponse>> getAllProjects(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name,asc") String sort) {
        return ApiResponse.<PageResponse<ProjectResponse>>builder()
                .result(projectService.getAllProject(page, size, sort))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping("{id}")
    public ProjectResponse getProjectById(@PathVariable @NonNull Integer id) {
        return projectService.getProjectById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    @GetMapping("{name}/name")
    public ProjectResponse getProjectByName(@PathVariable @NonNull String name) {
        return projectService.getProjectByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ProjectResponse updateProject(
            @PathVariable @NonNull Integer id, @RequestBody ProjectCreationRequest request) {
        return projectService.updateProject(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteProject(@PathVariable @NonNull Integer id) {
        projectService.deleteProject(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/requests")
    public ApiResponse<List<ProjectResponse>> searchRequestsOfSpecificProject(
            @PathVariable @NonNull Integer id, @RequestParam(value = "username", required = false) String username) {
        return ApiResponse.<List<ProjectResponse>>builder().build();
    }
}
