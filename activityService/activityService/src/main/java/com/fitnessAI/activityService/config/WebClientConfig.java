package com.fitnessAI.activityService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    //allow webClient to resolve the service name with via  eureka
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
    @Bean
    //it will point to user service
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return  webClientBuilder.baseUrl("http://USER-SERVICE").build();
    }
}
