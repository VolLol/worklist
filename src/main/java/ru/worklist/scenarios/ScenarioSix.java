package ru.worklist.scenarios;

import org.springframework.stereotype.Component;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioSix extends ScenarioBase {


    private final
    WorkRepository workRepository;

    private final
    UserRepository userRepository;

    public ScenarioSix(WorkRepository workRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }


    public void execute() {
        UserEntity user = userRepository.findByUserId(1L);
        List<WorkEntity> worksWithTags = workRepository.findAllWorksWithTags(user);
        if (!worksWithTags.isEmpty()) {
            for (WorkEntity work : worksWithTags) {
                List<String> workOut = new ArrayList<>();
                workOut.add(String.valueOf(work.getId()));
                workOut.add(work.getSummary());
                workOut.add(work.getDescribe());
                data.add(workOut);
            }
            columnNames.add("work_id");
            columnNames.add("summary");
            columnNames.add("describe");
            message = "This works with tags";
        } else {
            message = "Not exist works with tags";
        }
        showTable();
    }
}
