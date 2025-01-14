package com.scaffold.ticketSystem.dto.request;

import com.scaffold.ticketSystem.enums.MessageType;
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
