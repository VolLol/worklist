package ru.worklist;

import org.springframework.stereotype.Component;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioOne extends PrintClass {

    //добавить автоматическое дополнение колонок


    public void execute(UserEntity user) {

        List<WorkEntity> works = workRepository.findAll(); //разобраться почему не работает поиск по дате?

        if (!works.isEmpty()) {
            for (WorkEntity work : works) {
                List<String> workOut = new ArrayList<>();
                workOut.add(String.valueOf(work.getId()));
                workOut.add(work.getSummary());
                workOut.add(work.getDescribe());
                data.add(workOut);
            }
            columnName.add("work_id");
            columnName.add("summary");
            columnName.add("describe");
        } else {
            System.out.println("Not exist done work for the last month");
        }
        message = "I found this works for the last month";
    }
}


