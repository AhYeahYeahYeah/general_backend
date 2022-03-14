package com.workflow.general_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GeneralBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralBackendApplication.class, args);
    }

}
