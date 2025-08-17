package com.fitness.AI_Service.service;

import com.fitness.AI_Service.model.Activity;
import com.fitness.AI_Service.model.Recommendation;
import com.fitness.AI_Service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {
    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        log.info("Received activity for processing: {}", activity.getId());
//        log.info("Generated recommendation: {}", aiService.generateRecommendation(activity));
        Recommendation recommendation = aiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }
}
