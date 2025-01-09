package com.scaffold.spring_boot.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
