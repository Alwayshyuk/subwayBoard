package me.sanghyuk.subwayboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SubwayBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubwayBoardApplication.class, args);
    }

}
