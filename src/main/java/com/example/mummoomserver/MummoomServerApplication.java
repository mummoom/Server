package com.example.mummoomserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MummoomServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MummoomServerApplication.class, args);
    }

}
