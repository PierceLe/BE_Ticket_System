package com.scaffold.spring_boot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListProjectResponse {
    Integer totalPages;
    Integer totalElements;
    Integer currentPage;
    Integer numberOfElements;
    List<ProjectResponse> data;
}
