package com.scaffold.ticketSystem.dto.request.unit;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitCreationRequest {
    String name;
}
