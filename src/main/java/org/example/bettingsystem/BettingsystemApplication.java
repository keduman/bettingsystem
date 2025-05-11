package org.example.bettingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BettingsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BettingsystemApplication.class, args);
    }

}
