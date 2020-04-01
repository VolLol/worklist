package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.worklist.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class PrintClass {

    protected List<String> columnName = new ArrayList<>();
    protected List<List<String>> data = new ArrayList<>();
    @Autowired
    protected WorkRepository workRepository;

    public void print() {
        for (String s : columnName) {
            System.out.print(s + ", ");
        }

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + " ");
            }
        }
    }
}



