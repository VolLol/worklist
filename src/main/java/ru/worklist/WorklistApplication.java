package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.worklist.entites.SubworkEntity;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.SubworkRepository;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class WorklistApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WorklistApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    SubworkRepository subworkRepository;

    String USER1_USERNAME = "Helen";
    public static final ZonedDateTime CURRENT_DATE = ZonedDateTime.of(2020, 4, 1, 1, 1, 1, 1, ZoneId.of("UTC"));

    @Override
    public void run(String... args) {
        System.out.println("IAMALIVE");
        initializationBaseData();


    }

    private void scenarioOneShowAllDoneWorkForLastMonth(UserEntity user) {
        List<WorkEntity> works = workRepository.findAllBeforeDateByUser(true,
                CURRENT_DATE.minusDays(30),
                user);
        if (!works.isEmpty()) {
            for (WorkEntity work : works) {
                System.out.println(work);
            }
        } else {
            System.out.println("Not exist done work for the last month");
        }
    }

    private void scenarioShowAllWillDoneTomorrow(UserEntity user) {
        List<WorkEntity> works3 = workRepository.findAllWillDoneTomorrow(user);
        if (!works3.isEmpty()) {
            for (WorkEntity work : works3) {
                System.out.println(work.getSummary() + " " + work.getDescribe());
            }
        } else {
            System.out.println("Not exist work for tomorrow");
        }
    }

    private void scenarioShowAllWillDoneInNextMonth(UserEntity user) {
        List<WorkEntity> works5 = workRepository.findAllWillDoneInNextMonth(user);
        if (!works5.isEmpty()) {
            for (WorkEntity work : works5) {
                System.out.println(work.getSummary() + " " + work.getDescribe());
            }
        } else {
            System.out.println("Not exist work for next month");
        }

    }

    public void initializationBaseData() {
        UserEntity user = UserEntity.builder()
                .username(USER1_USERNAME)
                .email("Helen@email.com")
                .cryptPassword("password")
                .build();
        if (userRepository.findByUsername(USER1_USERNAME) == null) {
            userRepository.save(user);
        }
        user = userRepository.findByUsername(USER1_USERNAME);
/*
        WorkEntity workEntity = WorkEntity.builder()
                .describe("Work 1 describe")
                .summary("1.work 1")
                .user(user)
                .build();
        if (workRepository.findBySummaryAndUser("1.work 1", user) == null) {
            workRepository.save(workEntity);
        } else {
            System.out.println("Work with this summary already exist");
        }

        workEntity = WorkEntity.builder()
                .describe("Work 2 describe for check result that show all works finished in last month")
                .summary("2.work 2")
                .isDone(true)
                .planFinishedDate(ZonedDateTime.of(2020, 2, 1, 1, 1, 1, 1, ZoneId.of("UTC")))
                .user(user)
                .build();
        workRepository.save(workEntity);
*/
       /* if (workRepository.findBySummaryAndUser("2.work 2", user) == null) {
            workRepository.save(workEntity);
        } else {
            System.out.println("Work with this summary already exist");
        }
        Чёт криво работает, проверить почему
        */
/*

        WorkEntity workEntity3 = WorkEntity.builder()
                .summary("3.work 3")
                .describe("Work for scenario that will done tomorrow")
                .planFinishedDate(CURRENT_DATE.plusDays(1))
                .user(user)
                .build();
        workRepository.save(workEntity3);

        WorkEntity workEntity4 = WorkEntity.builder()
                .summary("4.work 4")
                .describe("Work for scenario that will done tomorrow, but plus 1 hour")
                .planFinishedDate(CURRENT_DATE.plusDays(1).plusHours(1))
                .user(user)
                .build();
        workRepository.save(workEntity4);
*/
        /*WorkEntity workEntity5 = WorkEntity.builder()
                .summary("5.work 5")
                .describe("Work for scenario that will done in next month")
                .planFinishedDate(CURRENT_DATE.plusDays(40))
                .user(user)
                .build();
        workRepository.save(workEntity5);*/


        WorkEntity workEntity = WorkEntity.builder()
                .summary("6.work ")
                .describe("Work fo scenario show that work has subwork")
                .user(user)
                .build();
        workRepository.save(workEntity);
        SubworkEntity subworkEntity = SubworkEntity.builder()
                .summary("One")
                .work(workEntity)
                .build();
        subworkRepository.save(subworkEntity);
        subworkEntity = SubworkEntity.builder()
                .summary("Two")
                .work(workEntity)
                .build();
        subworkRepository.save(subworkEntity);
        workRepository.save(workEntity);
        System.out.println("SUCSEE " + workEntity.getSummary());

        workRepository.findAllWorksWithSubworks(user);

    }


}
