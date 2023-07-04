# ecommerceWebsite
An ecommerce backend using springboot.

Some points:
1. Run zipkin via docker run -d -p 9411:9411 openzipkin/zipkin to make sure zipkin is up before making any calls else it would through error.
2. Service registry should up to make sure services are registered to it.
3. To make calls to orderService which also use services from productService, make sure product service is up and running.
