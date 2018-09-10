package pl.lodz.p.edu;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {
    @Autowired
    private Controller controller;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

    @PostConstruct
    public void init() throws IOException {
        //      controller.getAllStations();
        //      controller.addAllStations();
    }


}
