package com.example.mummoomserver;

import com.example.mummoomserver.oauthlogin.config.properties.AppProperties;
import com.example.mummoomserver.oauthlogin.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class MummoomServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MummoomServerApplication.class, args);
    }
}
