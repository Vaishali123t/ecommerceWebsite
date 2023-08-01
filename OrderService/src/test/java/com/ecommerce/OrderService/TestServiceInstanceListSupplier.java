package com.ecommerce.OrderService;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/* This class is used for retriving instances of all services that are registered with service registry
 */

public class TestServiceInstanceListSupplier implements ServiceInstanceListSupplier {


    @Override
    public String getServiceId() {
        return null;
    }

    @Override
    public Flux<List<ServiceInstance>> get(Request request) {

        List<ServiceInstance> res= new ArrayList<>();
        res.add(new DefaultServiceInstance("PRODUCT-SERVICE","PRODUCT-SERVICE","localhost",8671,false));
        res.add(new DefaultServiceInstance("AUTH-SERVICE","AUTH-SERVICE","localhost",8671,false));
        return Flux.just(res);
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return null;
    }
}
