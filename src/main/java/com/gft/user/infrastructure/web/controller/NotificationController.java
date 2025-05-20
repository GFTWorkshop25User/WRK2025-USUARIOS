package com.gft.user.infrastructure.web.controller;


import com.gft.user.application.notification.usecase.GetUserNotificationsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class NotificationController {

    GetUserNotificationsUseCase getUserNotificationsUseCase;

    @GetMapping("/users/{id}/notifications")
    public ResponseEntity<?> getUserNotifications(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserNotificationsUseCase.execute(id));
    }

}
