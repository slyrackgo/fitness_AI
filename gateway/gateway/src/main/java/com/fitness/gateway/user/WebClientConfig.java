//package com.fitnessAI.activityService.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//
////    @Bean
////    @LoadBalanced
////    public WebClient.Builder webClientBuilder() {
////        return WebClient.builder();
////    }
//
//
//    @Bean
//    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
//        return webClientBuilder
//                .baseUrl("http://USER-SERVICE")
//                .build();
//    }
//}
//

package com.fitness.gateway.user;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    }
}
