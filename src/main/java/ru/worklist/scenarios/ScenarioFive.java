package ru.worklist.scenarios;

import org.springframework.stereotype.Component;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioFive extends ScenarioBase {

    private final
    WorkRepository workRepository;

    private final
    UserRepository userRepository;


    public ScenarioFive(WorkRepository workRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    public void execute() {
        long days = 5L;
        UserEntity user = userRepository.findByUserId(1L);
        List<WorkEntity> worksForNDays = workRepository.findAllForNdays(user, CURRENT_DATE.plusDays(days));
        if (!worksForNDays.isEmpty()) {
            for (WorkEntity work : worksForNDays) {
                List<String> workOut = new ArrayList<>();
                workOut.add(String.valueOf(work.getId()));
                workOut.add(work.getSummary());
                workOut.add(work.getDescribe());
                data.add(workOut);
            }
            columnNames.add("work_id");
            columnNames.add("summary");
            columnNames.add("describe");
            message = "I found this works for " + days + " days";

        } else {
            message = "Not found works for " + days + " days";
        }
        showTable();
    }
}
