package com.scaffold.spring_boot.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// null attribute will not being shown
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    // default code is successful
    private int code = 200;
    private String message;
    private T result;
}
