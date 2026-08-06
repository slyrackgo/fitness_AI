package com.fitnessAI.activityService.service;

import com.fitnessAI.activityService.ActivityRepository;
import com.fitnessAI.activityService.dto.ActivityRequest;
import com.fitnessAI.activityService.dto.ActivityResponse;
import com.fitnessAI.activityService.model.Activity;
import com.fitnessAI.activityService.model.ActivityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    private ActivityRepository activityRepository;
    private UserValidationService userValidationService;
    private RabbitTemplate rabbitTemplate;
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        activityRepository = mock(ActivityRepository.class);
        userValidationService = mock(UserValidationService.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        activityService = new ActivityService(activityRepository, userValidationService, rabbitTemplate);
    }

    @Test
    void trackActivity_validUser_savesAndPublishes() {
        ActivityRequest req = new ActivityRequest();
        req.setUserId("user-123");
        req.setType(ActivityType.RUNNING);
        req.setDuration(30);
        req.setCaloriesBurned(250);
        req.setStartTime(LocalDateTime.now());

        when(userValidationService.validateUser("user-123")).thenReturn(true);

        Activity saved = Activity.builder()
                .id("act-1")
                .userId(req.getUserId())
                .type(req.getType())
                .duration(req.getDuration())
                .caloriesBurned(req.getCaloriesBurned())
                .startTime(req.getStartTime())
                .build();

        when(activityRepository.save(any(Activity.class))).thenReturn(saved);

        ActivityResponse resp = activityService.trackActivity(req);

        assertNotNull(resp);
        assertEquals("act-1", resp.getId());
        verify(activityRepository, times(1)).save(any(Activity.class));
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(Activity.class));

        ArgumentCaptor<Activity> payloadCaptor = ArgumentCaptor.forClass(Activity.class);
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), payloadCaptor.capture());
        assertEquals(saved, payloadCaptor.getValue());
    }

    @Test
    void trackActivity_invalidUser_throws() {
        ActivityRequest req = new ActivityRequest();
        req.setUserId("bad-user");

        when(userValidationService.validateUser("bad-user")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> activityService.trackActivity(req));
        verify(activityRepository, never()).save(any());
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any());
    }
}
