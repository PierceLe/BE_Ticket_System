package com.scaffold.spring_boot.dto.request.user_update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateUnitRequest {
    Integer unit;
}
