package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.worklist.scenarios.*;

@SpringBootApplication
public class WorklistApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WorklistApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Autowired
    ScenarioOne scenarioOne;

    @Autowired
    ScenarioTwo scenarioTwo;

    @Autowired
    ScenarioThree scenarioThree;

    @Autowired
    ScenarioFour scenarioFour;

    @Autowired
    ScenarioFive scenarioFive;

    @Autowired
    ScenarioSix scenarioSix;

    @Autowired
    ScenarioInitializationBaseData scenarioInitializationBaseData;

    @Override
    public void run(String... args) {
        System.out.println("IAMALIVE");

    }


}





