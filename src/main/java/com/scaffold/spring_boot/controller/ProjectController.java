package com.scaffold.spring_boot.controller;

import java.util.List;

import com.scaffold.spring_boot.dto.response.ListProjectResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.scaffold.spring_boot.dto.response.ApiResponse;
import com.scaffold.spring_boot.dto.request.project.ProjectCreationRequest;
import com.scaffold.spring_boot.dto.response.ProjectResponse;
import com.scaffold.spring_boot.service.ProjectService;

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
    public ApiResponse<ListProjectResponse> getAllProjects(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        return projectService.getAllProject(page, size, sort);
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
