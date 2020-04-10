package ru.worklist.scenarios;

import org.springframework.stereotype.Component;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioOne extends ScenarioBase {

    private final WorkRepository workRepository;
    private final UserRepository userRepository;

    public ScenarioOne(WorkRepository workRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    public void execute() {
        UserEntity user = userRepository.findByUserId(1L);
        List<WorkEntity> works = workRepository.findAllBeforeDateByUser(true,
                CURRENT_DATE, user);
        if (!works.isEmpty()) {
            for (WorkEntity work : works) {
                List<String> workOut = new ArrayList<>();
                workOut.add(String.valueOf(work.getId()));
                workOut.add(work.getSummary());
                workOut.add(work.getDescribe());
                data.add(workOut);
            }
            //добавить автоматическое дополнение колонок
            columnNames.add("work_id");
            columnNames.add("summary");
            columnNames.add("describe");
            message = "I found this works for the last month";
        } else {
            message = "Not exist done work for the last month";
        }
        showTable();
    }

}


