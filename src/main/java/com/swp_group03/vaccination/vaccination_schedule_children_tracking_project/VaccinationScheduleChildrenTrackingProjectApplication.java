package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@SpringBootApplication
public class VaccinationScheduleChildrenTrackingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccinationScheduleChildrenTrackingProjectApplication.class, args);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void openSwaggerUi() {
        String url = "http://localhost:8080/swagger-ui/index.html";
        System.out.println("Opening Swagger UI: " + url);

        try {
            new ProcessBuilder("cmd", "/c", "start", url).start(); // Lệnh mở trình duyệt trên Windows
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
