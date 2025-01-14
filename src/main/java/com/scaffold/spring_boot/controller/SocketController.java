package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.request.SocketMessage;
import com.scaffold.spring_boot.service.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private final SocketService socketService;

    @MessageMapping("/notification")
    public SocketMessage sendNotification(
            @Payload SocketMessage socketMessage
    ) {
        return socketService.sendNotification(socketMessage);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public SocketMessage sendMessage (
            @Payload SocketMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public SocketMessage addUser (
            SocketMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
        return chatMessage;
    }
}
