package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.worklist.entites.SubworkEntity;
import ru.worklist.entites.TagEntity;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.SubworkRepository;
import ru.worklist.repository.TagRepository;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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

    @Autowired
    TagRepository tagRepository;

    private String USER1_USERNAME = "Helen";
    private static final ZonedDateTime CURRENT_DATE = ZonedDateTime.of(2020, 4, 1, 1, 1, 1, 1, ZoneId.of("UTC"));

    @Autowired
    ScenarioOne scenarioOne;


    @Override
    public void run(String... args) {
        System.out.println("IAMALIVE");
        //initializationBaseData();

       scenarioOne.execute();
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


    private void scenarioTwoShowAllWillDoneTomorrow(UserEntity user) {
        List<WorkEntity> works3 = workRepository.findAllWillDoneTomorrow(user);
        if (!works3.isEmpty()) {
            for (WorkEntity work : works3) {
                System.out.println(work.getSummary() + " " + work.getDescribe());
            }
        } else {
            System.out.println("Not exist work for tomorrow");
        }
    }

    private void scenarioThreeShowAllWillDoneInNextMonth(UserEntity user) {
        List<WorkEntity> works5 = workRepository.findAllWillDoneInNextMonth(user);
        if (!works5.isEmpty()) {
            for (WorkEntity work : works5) {
                System.out.println(work.getSummary() + " " + work.getDescribe());
            }
        } else {
            System.out.println("Not exist work for next month");
        }

    }

    private void scenarioFoureShowAllWorksWithSubworks(UserEntity user) {
        List<SubworkEntity> subworkEntityList = subworkRepository.findAll();
        if (!subworkEntityList.isEmpty()) {
            Set<Long> uniqIds = new HashSet<>();

            for (SubworkEntity subwork : subworkEntityList) {
                uniqIds.add(subwork.getWork().getId());
            }

            ArrayList<Long> workIds = new ArrayList<>(uniqIds);

            List<WorkEntity> workListWithSubworks = workRepository.findByIds(user, workIds);
            for (WorkEntity work : workListWithSubworks) {
                System.out.println(work);
            }
        } else System.out.println("Works with subworks not exist");
    }


    public void scenarioFiveShowAllForNDays(Long days, UserEntity user) {
        List<WorkEntity> worksForNDays = workRepository.findAllForNdays(user, CURRENT_DATE.plusDays(days));

        if (!worksForNDays.isEmpty()) {
            for (WorkEntity work : worksForNDays) {
                System.out.println(work.getId() + " " + work.getSummary() + " " + work.getDescribe());
            }
        } else {
            System.out.println("Not found works for " + days + " days");
        }
    }

    public void scenarioSixShowAllWorksWithTags(UserEntity user) {
        List<WorkEntity> worksWithTags = workRepository.findAllWorksWithTags(user);
        if (!worksWithTags.isEmpty()) {
            System.out.println("Exist works with tags");
        } else {
            System.out.println("Not exist works with tags");
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

        workEntity = WorkEntity.builder()
                .describe("Work 2 describe for check result that show all works finished in last month")
                .summary("2.work 2")
                .isDone(true)
                .planFinishedDate(ZonedDateTime.of(2020, 3, 2, 1, 1, 1, 1, ZoneId.of("UTC")))
                .user(user)
                .build();
        workRepository.save(workEntity);

        workEntity = WorkEntity.builder()
                .describe("Work 2.1 describe for check result that show all works finished in last month")
                .summary("2.work 2.1")
                .isDone(true)
                .planFinishedDate(ZonedDateTime.of(2020, 3, 2, 1, 1, 1, 1, ZoneId.of("UTC")))
                .user(user)
                .build();
        workRepository.save(workEntity);

        if (workRepository.findBySummaryAndUser("2.work 2", user) == null) {
            workRepository.save(workEntity);
        } else {
            System.out.println("Work with this summary already exist");
        }
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

        WorkEntity workEntity5 = WorkEntity.builder()
                .summary("5.work 5")
                .describe("Work for scenario that will done in next month")
                .planFinishedDate(CURRENT_DATE.plusDays(40))
                .user(user)
                .build();
        workRepository.save(workEntity5);

        WorkEntity workEntity6 = WorkEntity.builder()
                .user(user)
                .summary("6.work ")
                .describe("Work for show all works with subworks")
                .build();
        workRepository.save(workEntity6);
        SubworkEntity subworkEntity = SubworkEntity.builder()
                .work(workEntity6)
                .summary("One")
                .build();
        subworkRepository.save(subworkEntity);

        subworkEntity = SubworkEntity.builder()
                .work(workEntity6)
                .summary("two")
                .build();
        subworkRepository.save(subworkEntity);

        long days = 3L;
        for (long i = 0; i <= days; i++) {
            WorkEntity workEntity7 = WorkEntity.builder()
                    .summary("7.work 7")
                    .describe("Work for scenario that will next " + days + " day")
                    .planFinishedDate(CURRENT_DATE.plusDays(i))
                    .user(user)
                    .build();
            workRepository.save(workEntity7);
        }

        String stringOfTags = " one shfblkj , two, three,";
        String[] arrayOfTagsWithoutId = stringOfTags.split(",");
        Set<TagEntity> setOfTags = new HashSet<>();
        List<TagEntity> tagsFromRepository = tagRepository.findAll();
        List<String> listTagsWithoutId = new ArrayList<>();
        for (TagEntity tag : tagsFromRepository) {
            listTagsWithoutId.add(tag.getTagText());
        }
        WorkEntity workEntity8 = new WorkEntity();
        Set<WorkEntity> setOfWorks = new HashSet<>();
        setOfWorks.add(workEntity8);
        for (String str : arrayOfTagsWithoutId) {
            if (!listTagsWithoutId.contains(str)) {
                TagEntity tag = TagEntity.builder()
                        .tagText(str)
                        .works(setOfWorks)
                        .build();
                setOfTags.add(tag);
                tagRepository.save(tag);

            } else {
                System.out.println("Tag " + str + " contains in database");
            }

        }

        workEntity8 = WorkEntity.builder()
                .user(user)
                .summary("8.work 8")
                .describe("Work for scenario add tags")
                .tags(setOfTags)
                .build();
        workRepository.save(workEntity8);


        System.out.println("Stop here");


        System.out.println("Stop here");

    }


}





