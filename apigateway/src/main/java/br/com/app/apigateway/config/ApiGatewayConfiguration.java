package br.com.app.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();

        List<String> services = List.of("book-service", "exchange-service");

        services.forEach(service ->
                routes.route(service,
                        r -> r.path("/" + service + "/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://" + service))
        );

        return routes.build();
    }
}