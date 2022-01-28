package com.example.mummoomserver;

import com.example.mummoomserver.oauthlogin.OauthLoginApplication;
import com.example.mummoomserver.oauthlogin.config.properties.AppProperties;
import com.example.mummoomserver.oauthlogin.config.properties.CorsProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

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
