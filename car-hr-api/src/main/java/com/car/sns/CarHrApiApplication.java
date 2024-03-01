package com.car.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CarHrApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarHrApiApplication.class, args);
    }

}
