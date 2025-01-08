package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.project.ProjectCreationRequest;
import com.scaffold.spring_boot.dto.response.ProjectResponse;
import com.scaffold.spring_boot.entity.Project;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.ProjectRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.scaffold.spring_boot.repository.RequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper;



}
