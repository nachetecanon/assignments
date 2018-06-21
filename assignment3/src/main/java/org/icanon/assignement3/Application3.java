package org.icanon.assignement3;

import org.icanon.assignements.model.StatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application3 {

    public static void main(String[] args) {
        SpringApplication.run(Application3.class, args);
    }

    @Bean
    public StatService statService() {
        return new StatService();
    }


}
