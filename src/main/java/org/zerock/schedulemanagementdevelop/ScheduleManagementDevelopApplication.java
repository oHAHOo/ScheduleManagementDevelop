package org.zerock.schedulemanagementdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScheduleManagementDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleManagementDevelopApplication.class, args);
    }

}
