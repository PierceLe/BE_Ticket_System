package com.scaffold.spring_boot.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.scaffold.spring_boot.dto.response.ListProjectResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scaffold.spring_boot.dto.response.ApiResponse;
import com.scaffold.spring_boot.dto.request.project.ProjectCreationRequest;
import com.scaffold.spring_boot.dto.response.ProjectResponse;
import com.scaffold.spring_boot.entity.Project;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectResponse createProject(ProjectCreationRequest request) {
        Boolean projectExist = projectRepository.existsByName(request.getName());

        if (projectExist) {
            throw new AppException(ErrorCode.UNIT_EXISTED);
        }
        Project project = modelMapper.map(request, Project.class);
        projectRepository.save(project);
        return modelMapper.map(project, ProjectResponse.class);
    }

    public ApiResponse<ListProjectResponse> getAllProject(Integer page, Integer size, String[] sort) {
        String sortField = sort[0]; //name
        String sortDirection = sort[1]; //asc
        Sort.Direction direction = Objects.equals(sortDirection, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<Project> projects = projectRepository.listProjects(pageable);

        // Map the list of projects to a list of ProjectResponse
        List<ProjectResponse> projectResponses = projects.stream()
                .map(project -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());

        ListProjectResponse response = ListProjectResponse.builder()
                .totalPages(projects.getTotalPages())
                .totalElements((int) projects.getTotalElements())
                .currentPage(projects.getNumber() + 1)
                .numberOfElements(projects.getNumberOfElements())
                .data(projectResponses)
                .build();

        return ApiResponse.<ListProjectResponse>builder()
                .result(response)
                .build();
    }

    public ProjectResponse getProjectById(Integer id) {
        Project project =
                projectRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PROJECT_ID_NOT_EXISTED));

        return modelMapper.map(project, ProjectResponse.class);
    }

    public ProjectResponse getProjectByName(String name) {
        Project project = projectRepository
                .findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NAME_NOT_EXISTED));

        return modelMapper.map(project, ProjectResponse.class);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    public ProjectResponse updateProject(Integer id, ProjectCreationRequest request) {
        Project project =
                projectRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PROJECT_ID_NOT_EXISTED));

        if (
                projectRepository.existsByName(request.getName())
                && !project.getName().equals(request.getName())
        ) {
            throw new AppException(ErrorCode.PROJECT_NAME_EXISTED);
        }

        project.setName(request.getName());
        return modelMapper.map(projectRepository.save(project), ProjectResponse.class);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }
}
