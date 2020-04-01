package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

public class ScenarioOne extends PrintClass {

    //добавить автоматическое дополнение колонок


    public void execute() {
        this.columnName.add("work_id");
        this.columnName.add("summary");
        this.columnName.add("describe");

        try {
            List<WorkEntity> works = workRepository.findAll();

            List<String> workOut = new ArrayList<>();
            if (!works.isEmpty()) {
                for (WorkEntity work : works) {
                    System.out.println(work);
                    workOut.add(String.valueOf(work.getId()));
                    workOut.add(work.getSummary());
                    workOut.add(work.getDescribe());
                    data.add(workOut);
                }
            } else {
                System.out.println("Not exist done work for the last month");
            }
        } catch (NullPointerException e) {
            System.err.println("FUCK its " + e);
        }
    }
}


