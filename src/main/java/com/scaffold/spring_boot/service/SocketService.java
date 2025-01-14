package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.SocketMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @PreAuthorize("#socketMessage.senderId == authentication.name")
    public SocketMessage sendNotification(SocketMessage socketMessage) {
        String destination = "/topic/notification/" + socketMessage.getReceiverId();
        messagingTemplate.convertAndSend(destination, socketMessage.getContent());
        return socketMessage;
    }
}
