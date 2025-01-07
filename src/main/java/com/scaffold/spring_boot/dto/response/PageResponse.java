package com.scaffold.spring_boot.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int totalPage;
    int pageSize;
    long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();

}
