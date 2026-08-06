package com.fitness.AI_Service.service;

import com.fitness.AI_Service.model.Activity;
import com.fitness.AI_Service.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityAIServiceTest {

    private GeminiService geminiService;
    private ActivityAIService activityAIService;

    @BeforeEach
    void setUp() {
        geminiService = mock(GeminiService.class);
        activityAIService = new ActivityAIService(geminiService);
    }

    @Test
    void generateRecommendation_malformedAiResponse_returnsDefaultRecommendation() {
        Activity activity = Activity.builder()
                .id("act-1")
                .userId("user-1")
                .type("run")
                .duration(30)
                .caloriesBurned(200)
                .startTime(LocalDateTime.now())
                .build();

        // Simulate malformed response that will cause parsing to fail
        when(geminiService.getAnswer(anyString())).thenReturn("not a json");

        Recommendation rec = activityAIService.generateRecommendation(activity);

        assertNotNull(rec);
        assertEquals(activity.getId(), rec.getActivityId());
        assertTrue(rec.getRecommendation().contains("Unable to generate") || rec.getImprovements() != null);
    }
}
