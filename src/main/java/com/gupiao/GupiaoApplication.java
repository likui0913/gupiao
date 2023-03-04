package com.gupiao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@EnableAsync
public class GupiaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GupiaoApplication.class, args);
    }

}
