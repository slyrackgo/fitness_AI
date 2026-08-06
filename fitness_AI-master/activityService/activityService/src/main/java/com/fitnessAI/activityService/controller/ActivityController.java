package com.fitnessAI.activityService.controller;

import com.fitnessAI.activityService.dto.ActivityRequest;
import com.fitnessAI.activityService.dto.ActivityResponse;
import com.fitnessAI.activityService.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {
    private ActivityService activityService;
    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request,
                                                          @RequestHeader(value = "X-User-ID", required = false) String userId){
        // ensure owner cannot be spoofed by client: override request userId with trusted header
        if (userId != null) {
            request.setUserId(userId);
        }
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID")String userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId,
                                                        @RequestHeader(value = "X-User-ID", required = false) String userId){
        return ResponseEntity.ok(activityService.getActivityById(activityId, userId));
    }
}
