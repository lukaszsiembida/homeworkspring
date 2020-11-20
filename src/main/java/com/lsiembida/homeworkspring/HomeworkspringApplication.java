package com.lsiembida.homeworkspring;

import com.lsiembida.homeworkspring.config.CompanyInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {CompanyInfo.class}) // dołączenie konfiguracji z application.properties , dodanie beana CompanyInfo
@SpringBootApplication
public class HomeworkspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkspringApplication.class, args);
    }

}
