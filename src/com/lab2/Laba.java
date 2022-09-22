package com.lab2;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс для запуска программы
 */
public class Laba {


  public static void main(String[] args) {

    UI myUI = new UI();

    myUI.menu();
  }

}

/**
 * Интерфейс, реализующий нумерацию вариантов выбора для switch()
 */
interface menuEnum {

  int PRINT_HUMANS = 1,
      ADD_VOID_HUMAN = 2,
      ADD_FILLED_HUMAN = 3,
      DELETE_HUMAN = 4,
      EDIT_HUMAN = 5,
      SORT_HUMANS = 6,
      EXIT = 7;

  int CHANGE_NAME = 1,
      CHANGE_SURNAME = 2,
      CHANGE_BIRTHDAY = 3,
      CHANGE_GENDER = 4,
      CHANGE_HEIGHT = 5,
      CHANGE_WEIGHT = 6,
      EXIT_TO_MENU = 7;

  int SORT_BY_BIRTHDAY = 1,
      SORT_BY_HEIGHT = 2,
      SORT_BY_WEIGHT = 3,
      SORT_BY_NAME = 4,
      SORT_BY_SURNAME = 5,
      SORT_BY_FULLNAME = 6,
      SORT_BY_GENDER = 7;
}

/**
 * Основной класс интерфейса
 */
class UI implements menuEnum {


  /**
   * Поток вывода, поддерживающий русские символы
   */
  private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

  /**
   * Экземпляр класса содержащего методы ввода разных типов
   */
  private final Inputer inp = new Inputer();

  /**
   * Основной список людей
   */
  private final ArrayList<Human> humans = new ArrayList<>();


  /**
   * Метод основного меню работы
   */
  public void menu() {
    this.humans.add(new Human(25, 8, 2003, 1.85f, 71, "Leo", "Sokolov", true));
    this.humans.add(new Human(31, 10, 2008, 1.65f, 51, "Katya", "Sokolova", false));
    this.humans.add(new Human(19, 12, 2003, 1.76f, 56, "Arisha", "Demekhina", false));

    int choice;
    do {
      this.out.println("""
          1. Вывести список людей
          2. Добавить незаполненного человека к списку
          3. Добавить человека, заполняя данные
          4. Удалить человека по индексу
          5. Отредактировать данные человека по индексу
          6. Отсортировать список людей
          7. Выйти из программы
          """);

      choice = inp.getInt();
      switch (choice) {
        case PRINT_HUMANS -> this.printHumans();
        case ADD_VOID_HUMAN -> this.addVoidHuman();
        case ADD_FILLED_HUMAN -> this.addParamHuman();
        case DELETE_HUMAN -> {
          out.println("Введите индекс человека в списке:");
          int index = inp.getInt();
          if (index < 1 || (index - 1) >= humans.size()) {
            out.println("Некорректный индекс");
          } else {
            deleteHuman(index);
          }
        }
        case EDIT_HUMAN -> {
          out.println("Введите индекс человека в списке:");
          int index = inp.getInt();
          if (index < 0 || (index - 1) >= humans.size()) {
            out.println("Некорректный индекс");
          } else {
            editHuman(index);
          }
        }
        case SORT_HUMANS -> sortHumans();
        default -> {
          if (choice != EXIT) {
            this.out.println("Некорректный ввод");
          }
        }

      }
    } while (choice != EXIT);
  }

  /**
   * Метод, реализующий сортировку по выбранному в нем полю
   */
  private void sortHumans() {
    this.out.println("""
        1. Сортировка по дате рождения
        2. Сортировка по росту
        3. Сортировка по весу
        4. Сортировка по Имени
        5. Сортировка по фамилии
        6. Сортировка по имени и фамилии
        7. Сортировка по полу
        """);
    int choice = inp.getInt();
    switch (choice) {
      case SORT_BY_BIRTHDAY -> {
        humans.sort(Comparator.comparing(Human::getBDateInDays));
        Collections.reverse(humans);
      }
      case SORT_BY_HEIGHT -> humans.sort(Comparator.comparing(Human::getHeight));
      case SORT_BY_WEIGHT -> humans.sort(Comparator.comparing(Human::getWeight));
      case SORT_BY_NAME -> humans.sort(Comparator.comparing(Human::getName));
      case SORT_BY_SURNAME -> humans.sort(Comparator.comparing(Human::getSurname));
      case SORT_BY_FULLNAME -> humans.sort(Comparator.comparing(Human::getFullName));
      case SORT_BY_GENDER -> humans.sort(Comparator.comparing(Human::getGender));
      default -> System.out.println("Неизвестный параметр");
    }

  }

  /**
   * Метод удаления человека из списка по индексу
   *
   * @param index индекс удаляемого человека
   */
  private void deleteHuman(int index) {
    this.out.println(humans.get(index - 1).getFullName() + " удален из списка");
    humans.remove(index - 1);
  }

  /**
   * Метод добавления человека с задаваемыми параметрами
   */
  private void addParamHuman() {
    int bDay = 0, bMonth = 0, bYear = 0;
    float height = 0, weight = 0;
    String name = null, surname = null;
    boolean gender = false;

    boolean isBirthday = false,
        isHeight = false,
        isWeight = false,
        isName = false,
        isSurname = false,
        isGender = false;

    while (!(isBirthday & isHeight & isWeight & isName & isSurname)) {

      if (!isBirthday) {
        this.out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
        String date = this.inp.getString();
        if (isDateCorrect(date)) {
          String[] dateList = date.split("\\.");
          bDay = Integer.parseInt(dateList[0]);
          bMonth = Integer.parseInt(dateList[1]);
          bYear = Integer.parseInt(dateList[2]);
          isBirthday = true;
        } else {
          this.out.println("Некорректная дата");
        }
      }

      if (!isHeight) {
        this.out.println("Введите рост в метрах:");
        float tmpHeight = this.inp.getFloat();
        if (tmpHeight <= 0) {
          this.out.println("Некорректный рост");
        } else {
          height = tmpHeight;
          isHeight = true;
        }
      }

      if (!isWeight) {
        this.out.println("Введите вес в килограммах:");
        float tmpWeight = this.inp.getFloat();
        if (tmpWeight <= 0) {
          this.out.println("Некорректный вес");
        } else {
          weight = tmpWeight;
          isWeight = true;
        }
      }

      if (!isName) {
        this.out.println("Введите имя:");
        String tmpName = this.inp.getString();
        if (tmpName == null) {
          this.out.println("Некорректный ввод");
        } else {
          name = tmpName;
          isName = true;
        }
      }

      if (!isSurname) {
        this.out.println("Введите имя:");
        String tmpSurname = this.inp.getString();
        if (tmpSurname == null) {
          this.out.println("Некорректный ввод");
        } else {
          surname = tmpSurname;
          isSurname = true;
        }
      }

      if (!isGender) {
        this.out.println("Введите пол (м/ж):");
        String tmpGender = this.inp.getString();
        if (Objects.equals(tmpGender.toLowerCase(), "м")) {
          gender = true;
          isGender = true;
        } else if (Objects.equals(tmpGender.toLowerCase(), "ж")) {
          gender = false;
          isGender = true;
        } else {
          this.out.println("Некорректный ввод");
        }
      }

    }

    humans.add(new Human(bDay, bMonth, bYear, height, weight, name, surname, gender));
  }

  /**
   * Метод добавления человека с незаполненными полями
   */
  private void addVoidHuman() {
    humans.add(new Human());
  }

  /**
   * Метод редактирования любых полей человека
   *
   * @param index индекс редактируемого человека
   */
  private void editHuman(int index) {
    Human humanToEdit = this.humans.get(index - 1);
    int choice;
    do {
      this.out.println("""
          1. Изменить имя
          2. Изменить фамилию
          3. Изменить дату рождения
          4. Изменить пол
          5. Изменить рост
          6. Изменить вес
          7. Выйти в меню
          """);
      choice = this.inp.getInt();
      switch (choice) {

        case CHANGE_NAME -> {
          this.out.println("Введите имя:");
          String name = this.inp.getString();
          if (name != null) {
            humanToEdit.setName(name);
          } else {
            this.out.println("Некорректное имя");
          }
        }

        case CHANGE_SURNAME -> {
          this.out.println("Введите фамилию:");
          String surname = this.inp.getString();
          if (surname != null) {
            humanToEdit.setSurname(surname);
          } else {
            this.out.println("Некорректная фамилия");
          }
        }

        case CHANGE_BIRTHDAY -> {
          this.out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
          String date = this.inp.getString();
          if (isDateCorrect(date)) {
            String[] dateList = date.split("\\.");
            int bDay = Integer.parseInt(dateList[0]);
            int bMonth = Integer.parseInt(dateList[1]);
            int bYear = Integer.parseInt(dateList[2]);
            humanToEdit.setBirthday(bDay, bMonth, bYear);
          } else {
            this.out.println("Некорректная дата");
          }
        }

        case CHANGE_GENDER -> {
          this.out.println("Введите пол (м/ж):");
          String tmpGender = this.inp.getString();
          if (Objects.equals(tmpGender.toLowerCase(), "м")) {
            humanToEdit.setGender(true);
          } else if (Objects.equals(tmpGender.toLowerCase(), "ж")) {
            humanToEdit.setGender(false);
          } else {
            this.out.println("Некорректный ввод");
          }

        }

        case CHANGE_HEIGHT -> {
          this.out.println("Введите рост в метрах:");
          float tmpHeight = this.inp.getFloat();
          if (tmpHeight <= 0) {
            this.out.println("Некорректный рост");
          } else {
            humanToEdit.setHeight(tmpHeight);
          }
        }

        case CHANGE_WEIGHT -> {
          this.out.println("Введите вес в килограммах:");
          float tmpWeight = this.inp.getFloat();
          if (tmpWeight <= 0) {
            this.out.println("Некорректный вес");
          } else {
            humanToEdit.setWeight(tmpWeight);
          }
        }
        case EXIT_TO_MENU -> this.out.println("Редактирование завершено");
        default -> this.out.println("Некорректный ввод!");
      }

    } while (choice != EXIT_TO_MENU);

  }

  /**
   * Метод вывода всего списка людей
   */
  private void printHumans() {
    int num = 1;

    for (Human human : this.humans) {
      this.out.printf("Человек №%d\n", num);
      this.out.println(human);

      num++;
    }
  }


  /**
   * Метод проверки вводимой даты на корректность посредством регулярного выражения
   */
  private static boolean isDateCorrect(String date) {
    String pattern = "(0[1-9]|[12]\\d|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
    Pattern pat = Pattern.compile(pattern);
    Matcher match = pat.matcher(date);

    return match.find();
  }

}