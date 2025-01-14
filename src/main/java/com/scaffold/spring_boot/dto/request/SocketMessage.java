package com.scaffold.spring_boot.dto.request;

import com.scaffold.spring_boot.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketMessage {
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType type;
}
