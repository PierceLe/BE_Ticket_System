package com.scaffold.spring_boot.service;

import org.springframework.stereotype.Service;

import com.scaffold.spring_boot.repository.RequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
}
