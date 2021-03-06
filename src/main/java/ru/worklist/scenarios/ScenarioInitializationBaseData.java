package ru.worklist.scenarios;

import org.springframework.stereotype.Component;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ScenarioInitializationBaseData {

    private final
    UserRepository userRepository;

    private final
    WorkRepository workRepository;

    private final
    SubworkRepository subworkRepository;

    private final
    TagRepository tagRepository;

    private String USER1_USERNAME = "Helen";
    private static final ZonedDateTime CURRENT_DATE = ZonedDateTime.of(2020, 4, 1, 1, 1, 1, 1, ZoneId.of("UTC"));

    public ScenarioInitializationBaseData(UserRepository userRepository, WorkRepository workRepository, SubworkRepository subworkRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.workRepository = workRepository;
        this.subworkRepository = subworkRepository;
        this.tagRepository = tagRepository;
    }

    public void execute() {
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

    }
}
