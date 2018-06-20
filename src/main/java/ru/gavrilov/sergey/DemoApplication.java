package ru.gavrilov.sergey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableKafka
@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
