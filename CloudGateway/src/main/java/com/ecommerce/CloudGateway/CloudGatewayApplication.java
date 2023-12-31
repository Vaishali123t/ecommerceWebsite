package com.ecommerce.CloudGateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableEurekaClient
public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}

	@Bean
	KeyResolver userKeySolver(){
		return exchange -> Mono.just("userKey");
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {

		// to configure the configurations for circuit breaker i.e. time to move from open to half open state etc
		// for now we are using default configs

		return factory -> factory.configureDefault(
				id-> new Resilience4JConfigBuilder(id)
						.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
						.build());
    }

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() { // not used currently
		return new RestTemplate();
	}

	@Bean
	public WebClient.Builder getWebClientBuilder(){ // not used currently
		return WebClient.builder();
	}

}
