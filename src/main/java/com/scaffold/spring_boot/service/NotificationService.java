package com.scaffold.spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
class NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String message, String userId) {
        String destination = "/topic/notification/" + userId;
        messagingTemplate.convertAndSend(destination, message);
    }
}
