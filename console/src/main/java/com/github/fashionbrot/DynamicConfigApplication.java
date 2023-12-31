package com.github.fashionbrot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DynamicConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicConfigApplication.class,args);
        log.info("Start to finish:{}", System.currentTimeMillis());
    }

}
