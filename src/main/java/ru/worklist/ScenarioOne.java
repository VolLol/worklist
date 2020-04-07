package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioOne extends BaseScenario {
    @Autowired
    protected WorkRepository workRepository;
    @Autowired
    protected UserRepository userRepository;

    public void execute() {
        UserEntity user = userRepository.findByUserId(1l);
        List<WorkEntity> works = workRepository.findAllBeforeDateByUser(true,
                CURRENT_DATE,user);
        if (!works.isEmpty()) {
            for (WorkEntity work : works) {
                List<String> workOut = new ArrayList<>();
                workOut.add(String.valueOf(work.getId()));
                workOut.add(work.getSummary());
                workOut.add(work.getDescribe());
                data.add(workOut);
            }
            //добавить автоматическое дополнение колонок
            columnName.add("work_id");
            columnName.add("summary");
            columnName.add("describe");
            message = "I found this works for the last month";
        } else {
            message = "Not exist done work for the last month";
        }
            print();
    }
}


