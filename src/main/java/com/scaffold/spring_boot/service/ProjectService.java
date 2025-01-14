package com.scaffold.spring_boot.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.scaffold.spring_boot.dto.request.project.ProjectCreationRequest;
import com.scaffold.spring_boot.dto.response.PageResponse;
import com.scaffold.spring_boot.dto.response.ProjectResponse;
import com.scaffold.spring_boot.entity.Project;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.ProjectRepository;
import com.scaffold.spring_boot.utils.SortUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final SortUtils sortUtils;

    public ProjectResponse createProject(ProjectCreationRequest request) {
        Boolean projectExist = projectRepository.existsByName(request.getName());

        if (projectExist) {
            throw new AppException(ErrorCode.PROJECT_NAME_EXISTED);
        }
        Project project = modelMapper.map(request, Project.class);
        projectRepository.save(project);
        return modelMapper.map(project, ProjectResponse.class);
    }

    public PageResponse<ProjectResponse> getAllProject(Integer page, Integer size, String sort) {

        List<Order> orders = sortUtils.generateOrder(sort);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orders));
        var pageData = projectRepository.listProjects(pageable);
        return PageResponse.<ProjectResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map((element) -> modelMapper.map(element, ProjectResponse.class))
                        .toList())
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

        if (projectRepository.existsByName(request.getName())
                && !project.getName().equals(request.getName())) {
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
