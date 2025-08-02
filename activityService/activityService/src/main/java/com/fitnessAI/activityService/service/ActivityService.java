package com.fitnessAI.activityService.service;

import com.fitnessAI.activityService.ActivityRepository;
import com.fitnessAI.activityService.dto.ActivityRequest;
import com.fitnessAI.activityService.dto.ActivityResponse;
import com.fitnessAI.activityService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final  UserValidationService userValidationService;
    private final ActivityRepository activityRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValidUser = userValidationService.validateUser(request.getUserId());


        if(!isValidUser){
            throw new RuntimeException("Invalid user: " + request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        //Publish to rabbitmq for AI processing
        try{
            //rabbit template
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        } catch (Exception e){
            log.error("Failed to publish activity to RabbitMQ: ", e);
        }

        return  maptoResponse(savedActivity);
    }

    //This method  will take the object of typeActivity and will convert it to the object of activityResponse
    private ActivityResponse maptoResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities  = activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::maptoResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::maptoResponse)
                .orElseThrow(() -> new RuntimeException("Activityu not found with id: " + activityId));
    }
}
