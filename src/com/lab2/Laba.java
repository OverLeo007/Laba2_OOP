package com.lab2;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;


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
      SORT_BY_FULL_NAME = 6,
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
        case DELETE_HUMAN -> this.deleteHuman();
        case EDIT_HUMAN -> editHuman();
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
      case SORT_BY_BIRTHDAY -> humans.sort(Comparator.comparing(Human::getBDate).reversed());
      case SORT_BY_HEIGHT -> humans.sort(Comparator.comparing(Human::getHeight));
      case SORT_BY_WEIGHT -> humans.sort(Comparator.comparing(Human::getWeight));
      case SORT_BY_NAME -> humans.sort(Comparator.comparing(Human::getName));
      case SORT_BY_SURNAME -> humans.sort(Comparator.comparing(Human::getSurname));
      case SORT_BY_FULL_NAME -> humans.sort(Comparator.comparing(Human::getFullName));
      case SORT_BY_GENDER -> humans.sort(Comparator.comparing(Human::getGender));
      default -> System.out.println("Неизвестный параметр");
    }

  }

  /**
   * Метод удаления человека из списка по индексу
   */
  private void deleteHuman() {
    out.println("Введите индекс человека в списке:");
    int index = inp.getInt();
    if (index < 1 || index > humans.size()) {
      out.println("Некорректный индекс");
    } else {
      this.out.println(humans.get(index - 1).getFullName() + " удален из списка");
      humans.remove(index - 1);
    }
  }

  /**
   * Метод добавления человека с задаваемыми параметрами
   */
  private void addParamHuman() {
    Human new_human = new Human();

    boolean isBirthday = false,
        isHeight = false,
        isWeight = false,
        isName = false,
        isSurname = false,
        isGender = false;

    while (!(isBirthday & isHeight & isWeight & isName & isSurname)) {

      if (!isBirthday) {
        this.out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
        isBirthday = new_human.setBirthday(this.inp.getString());
        if (!isBirthday) {
          this.out.println("Некорректная дата");
        }
      }

      if (!isHeight) {
        this.out.println("Введите рост в метрах:");
        isHeight = new_human.setHeight(this.inp.getFloat());
        if (!isHeight) {
          this.out.println("Некорректный рост");
        }
      }

      if (!isWeight) {
        this.out.println("Введите вес в килограммах:");
        isWeight = new_human.setWeight(this.inp.getFloat());
        if (!isWeight) {
          this.out.println("Некорректный вес");
        }
      }

      if (!isName) {
        this.out.println("Введите имя:");
        isName = new_human.setName(this.inp.getString());
        if (!isName) {
          this.out.println("Некорректный ввод");
        }
      }

      if (!isSurname) {
        this.out.println("Введите фамилию:");
        isSurname = new_human.setSurname(this.inp.getString());
        if (!isSurname) {
          this.out.println("Некорректный ввод");
        }
      }

      if (!isGender) {
        this.out.println("Введите пол (м/ж):");
        isGender = new_human.setGender(this.inp.getString());
        if (!isGender) {
          this.out.println("Некорректный ввод");
        }
      }
    }
    humans.add(new_human);
  }

  /**
   * Метод добавления человека с незаполненными полями
   */
  private void addVoidHuman() {
    humans.add(new Human());
  }

  /**
   * Метод редактирования любых полей человека
   */
  private void editHuman() {
    out.println("Введите индекс человека в списке:");
    int index = inp.getInt();
    if (index < 1 || index > humans.size()) {
      out.println("Некорректный индекс");
      return;
    }
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

        case CHANGE_NAME -> changeNameHandler(humanToEdit);
        case CHANGE_SURNAME -> changeSurnameHandler(humanToEdit);
        case CHANGE_BIRTHDAY -> changeBirthdayHandler(humanToEdit);
        case CHANGE_GENDER -> changeGenderHandler(humanToEdit);
        case CHANGE_HEIGHT -> changeHeightHandler(humanToEdit);

        case CHANGE_WEIGHT -> changeWeightHandler(humanToEdit);
        case EXIT_TO_MENU -> this.out.println("Редактирование завершено");
        default -> this.out.println("Некорректный ввод!");
      }

    } while (choice != EXIT_TO_MENU);
  }

  /**
   * Метод изменяющий имя выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   */
  private void changeNameHandler(Human humanToEdit) {
    this.out.println("Введите имя:");
    if (!humanToEdit.setName(this.inp.getString())) {
      this.out.println("Некорректное имя");
    }
  }

  /**
   * Метод изменяющий фамилию выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   */
  private void changeSurnameHandler(Human humanToEdit) {
    this.out.println("Введите фамилию:");
    if (!humanToEdit.setSurname(this.inp.getString())) {
      this.out.println("Некорректная фамилия");
    }
  }

  /**
   * Метод изменяющий день рождения выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   */
  private void changeBirthdayHandler(Human humanToEdit) {
    this.out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
    if (!humanToEdit.setBirthday(this.inp.getString())) {
      this.out.println("Некорректная дата");
    }
  }

  /**
   * Метод изменяющий пол выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   */
  private void changeGenderHandler(Human humanToEdit) {
    this.out.println("Введите пол (м/ж):");
    if (!humanToEdit.setGender(this.inp.getString())) {
      this.out.println("Некорректный ввод");
    }
  }

  /**
   * Метод изменяющий рост выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   */
  private void changeHeightHandler(Human humanToEdit) {
    this.out.println("Введите рост в метрах:");
    if (!humanToEdit.setHeight(this.inp.getFloat())) {
      this.out.println("Некорректный рост");
    }
  }

  /**
   * Метод изменяющий вес выбранного человека
   *
   * @param humanToEdit Изменяемый экземпляр человека
   */
  private void changeWeightHandler(Human humanToEdit) {
    this.out.println("Введите вес в килограммах:");
    if (!humanToEdit.setWeight(this.inp.getFloat())) {
      this.out.println("Некорректный вес");
    }
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
}