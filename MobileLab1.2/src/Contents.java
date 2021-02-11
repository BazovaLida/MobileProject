import java.util.*;

public class Contents{
    public static void main(String[] args) {
        String studentsStr = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; Лесик Сергій - ІО-82; Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81; Кочерук Давид - ІВ-83; Лихацька Юлія- ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; Снігурець Олег - ІВ-81; Роман Олександр - ІО-82; Дудка Максим - ІО-81; Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83; Грабко Михайло - ІВ-81; Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83; Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; Тарасенко Юлія - ІО-82; Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82";

// Завдання 1
// Заповніть словник, де:
// - ключ – назва групи
// - значення – відсортований масив студентів, які відносяться до відповідної групи

        Map<String, List<String>> groupedStudents = new HashMap<>();

// Ваш код починається тут

        String[] studentAndGroup = studentsStr.split("(( *\\- )+|; )");
        for (int i = 0; i < studentAndGroup.length; i++) {
            if(i % 2 == 1) { //group
                if (!groupedStudents.containsKey(studentAndGroup[i])) {
                    groupedStudents.put(studentAndGroup[i], new ArrayList<>());
                }
                groupedStudents.get(studentAndGroup[i]).add(studentAndGroup[i - 1]);
            }
        }
        groupedStudents.forEach((k, v) -> {
            Collections.sort(v);
        });

// Ваш код закінчується тут

        System.out.println("Завдання 1");
        System.out.println(groupedStudents + "\n");

// Дано масив з максимально можливими оцінками

        Integer[] points = {12, 12, 12, 12, 12, 12, 12, 16};

// Завдання 2
// Заповніть словник, де:
// - ключ – назва групи
// - значення – словник, де:
//   - ключ – студент, який відносяться до відповідної групи
//   - значення – масив з оцінками студента (заповніть масив випадковими значеннями, використовуючи функцію `randomValue(maxValue: Int) -> Int`)

        Map<String, Map<String, ArrayList<Integer>>> studentPoints = new HashMap<>();

// Ваш код починається тут

        groupedStudents.forEach((k, v) -> {
            Map <String, ArrayList<Integer>> internal = new HashMap<>();
            for (String studentName : v) {
                ArrayList<Integer> currPoints = new ArrayList<>();
                for (Integer point : points) {
                    currPoints.add(randomValue(point));
                }
                internal.put(studentName, currPoints);
            }
            studentPoints.put(k, internal);
            Collections.sort(v);
        });


// Ваш код закінчується тут

        System.out.println("Завдання 2");
        System.out.println(studentPoints + "\n");

// Завдання 3
// Заповніть словник, де:
// - ключ – назва групи
// - значення – словник, де:
//   - ключ – студент, який відносяться до відповідної групи
//   - значення – сума оцінок студента

        Map<String, Map<String, Integer>> sumPoints = new HashMap<>();

// Ваш код починається тут

        studentPoints.forEach((group, dict) ->{
            Map<String, Integer> currDict = new HashMap<>();
            dict.forEach((name, p) -> {
                Integer currSum = 0;
                for (Integer point : p) {
                    currSum += point;
                }
                currDict.put(name, currSum);
            });
            sumPoints.put(group, currDict);
        });

// Ваш код закінчується тут

        System.out.println("Завдання 3");
        System.out.println(sumPoints + "\n");

// Завдання 4
// Заповніть словник, де:
// - ключ – назва групи
// - значення – середня оцінка всіх студентів групи

        Map<String, Float> groupAvg = new HashMap<>();

// Ваш код починається тут

        sumPoints.forEach((group, dict) ->{
            Iterator<Integer> pValues = dict.values().iterator();
            Float average = 0.0f;
            int count = 0;
            while (pValues.hasNext()){
                count ++;
                average += pValues.next();
            }
            average /= count;
            groupAvg.put(group, average);
        });

// Ваш код закінчується тут

        System.out.println("Завдання 4");
        System.out.println(groupAvg + "\n");

// Завдання 5
// Заповніть словник, де:
// - ключ – назва групи
// - значення – масив студентів, які мають >= 60 балів

        Map<String, List<String>> passedPerGroup = new HashMap<>();

// Ваш код починається тут

        sumPoints.forEach((group, dict) ->{
            List<String> people = new ArrayList<>();
            dict.forEach((name, p) ->{
                if(p >= 60)
                    people.add(name);
            });
            passedPerGroup.put(group, people);
        });

// Ваш код закінчується тут

        System.out.println("Завдання 5");
        System.out.println(passedPerGroup + "\n");
    }

    private static Integer randomValue(Integer maxValue){
        Random random = new Random();
        return switch (random.nextInt(6)) {
            case 1 -> (int) Math.ceil(maxValue.floatValue() * 0.7);
            case 2 -> (int) Math.ceil(maxValue.floatValue() * 0.9);
            case 3, 4, 5 -> maxValue;
            default -> 0;
        };
    }
}

//