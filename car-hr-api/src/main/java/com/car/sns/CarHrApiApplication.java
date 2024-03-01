package com.car.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = {"com.car.*"})
public class CarHrSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarHrSnsApplication.class, args);
    }

}
