package org.tpr.api.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        r -> r.path("/api/parcel/**")
                                .filters(f -> f.addResponseHeader("X-Powered-By", "High Speed Delivery"))
                                .uri("http://localhost:8081")
                )
                .route(
                        r -> r.path("/api/user/**")
                                .filters(f -> f.addResponseHeader("X-Powered-By", "High Speed Delivery"))
                                .uri("http://localhost:8082")
                )
                .build();
    }
}
