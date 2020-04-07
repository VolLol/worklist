package ru.worklist;

import org.springframework.beans.factory.annotation.Autowired;
import ru.worklist.repository.WorkRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseScenario {

    protected List<String> columnName = new ArrayList<>();
    protected List<List<String>> data = new ArrayList<>();
    protected List<String> formatOfColumns = new ArrayList<>();//формат строки
    protected List<Integer> sizeOfColumn = new ArrayList<>();//длинна каждой строки
    protected int N = 30; //стандартный размер колонки
    protected static final ZonedDateTime CURRENT_DATE = ZonedDateTime.of(2020, 4, 1, 1, 1, 1, 1, ZoneId.of("UTC"));
    protected String message;//сообщение выводимое перед таблицей

    public void print() {
        System.out.println(message);
        for (String s : columnName) {
            int sLen = s.length();
            String args;
            if (s.contains("id")) {
                args = "|%-" + (N / 3) + "s|";
                sizeOfColumn.add(N / 3);
            } else if (s.contains("describe")) {
                args = "|%" + (N + 1) + "." + N + "s|";
                sizeOfColumn.add(N);
            } else {
                args = "|%-" + (sLen + 3) + ".15s|";
                sizeOfColumn.add(sLen + 3);
            }


            formatOfColumns.add(args);
            System.out.format(args, s);
        }

        for (List<String> datum : data) {
            System.out.println("\n");
            int countFreeSpace = 0;
            for (int j = 0; j < datum.size(); j++) {
                String freeSpace = "-";
                String formatString = "|%" + countFreeSpace + "s|";
                if (datum.get(j).length() >= N) { //если длинна строки больше чем 30 - то занимаемся её разделением
                    String str = datum.get(j); //создаём отдельно строку
                    int len = 0;//счётчик символов
                    while (len < str.length()) {  //пока счётчик меньше длинны строки делим строку и печатаем её
                        String newString;
                        if ((str.length() - len) > N) { //если разница между длинной строки и счётчиком символов больше 30, то выполняем деление строки
                            newString = str.substring(len, len + N);
                            System.out.format(formatOfColumns.get(j), newString);
                            System.out.println("\n");
                            System.out.format(formatString, freeSpace);
                            len = len + N;
                        } else { //если разница меньше то значит это конец строки
                            newString = str.substring(len);
                            System.out.format(formatOfColumns.get(j), newString);
                            len = str.length() + 10;
                        }

                    }
                } else {
                    System.out.format(formatOfColumns.get(j), datum.get(j));
                    countFreeSpace = countFreeSpace + sizeOfColumn.get(j) + 1; //счётчик свободного места
                }

            }
        }
    }
}



