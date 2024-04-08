package com.fir.backend;

import com.fir.backend.consumer.Matching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        new Matching().start();
        SpringApplication.run(BackendApplication.class, args);
    }

}
