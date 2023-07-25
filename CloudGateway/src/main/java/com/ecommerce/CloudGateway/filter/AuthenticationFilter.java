package com.ecommerce.CloudGateway.filter;

import com.ecommerce.CloudGateway.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @Autowired
    private JwtService jwtService;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (((exchange, chain) -> {

            if(routeValidator.isSecured.test(exchange.getRequest())){
                // header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){ // not found
                    throw new RuntimeException("missing authorization header"); // throw exception
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader=authHeader.substring(7); // just because postman sends headers with extra 7 spaces
                }

                try{
                    // REST call to AUTH service
//                    restTemplate.getForObject("http://AUTH-SERVICE/validate/"+authHeader,String.class);
//                    webClientBuilder.build()
//                            .get()
//                            .uri("http://AUTH-SERVICE/validate/"+authHeader)
//                            .retrieve()
//                            .bodyToMono(String.class)
//                            .block();

                    // the calls are not working. not able to make calls from a non-blocking service to a blocking service
                    // maybe i will have to change auth-service to a non-blocking service
                    // for now we call it using a function created at cloud gateway only
                    jwtService.validateToken(authHeader);

                }
                catch (Exception e){

                    throw new RuntimeException("unauthorized access token");
                }

            }


            return chain.filter(exchange);
        }));

    }

    public static class Config{

    }

}
