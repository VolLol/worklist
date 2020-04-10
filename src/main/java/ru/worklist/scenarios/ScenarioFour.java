package ru.worklist.scenarios;

import org.springframework.stereotype.Component;
import ru.worklist.entites.SubworkEntity;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;
import ru.worklist.repository.SubworkRepository;
import ru.worklist.repository.UserRepository;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ScenarioFour extends ScenarioBase {


    private final SubworkRepository subworkRepository;
    private final UserRepository userRepository;
    private final WorkRepository workRepository;

    public ScenarioFour(SubworkRepository subworkRepository, UserRepository userRepository, WorkRepository workRepository) {
        this.subworkRepository = subworkRepository;
        this.userRepository = userRepository;
        this.workRepository = workRepository;
    }

    public void execute(Long userId) {
        UserEntity user = userRepository.findByUserId(1L);
        List<SubworkEntity> subworkEntityList = subworkRepository.findAll();
        if (!subworkEntityList.isEmpty()) {
            Set<Long> uniqIds = new HashSet<>();

            for (SubworkEntity subwork : subworkEntityList) {
                uniqIds.add(subwork.getWork().getId());
            }

            ArrayList<Long> workIds = new ArrayList<>(uniqIds);

            List<WorkEntity> workListWithSubworks = workRepository.findByIds(user, workIds);
            for (WorkEntity work : workListWithSubworks) {
                List<String> workOut = new ArrayList<>();
                workOut.add(String.valueOf(work.getId()));
                workOut.add(work.getSummary());
                workOut.add(work.getDescribe());
                data.add(workOut);
            }
            columnNames.add("work_id");
            columnNames.add("summary");
            columnNames.add("describe");
            message = "I found this works with subworks";
            showTable();
        } else message = "Works with subworks not exist";
    }
}
