package com.lab2;


import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


interface Constants {

  float VERY_LOW_MASS = 16f,
      LOW_MASS = 18.5f,
      NORM_MASS = 25f,
      NEAR_TO_FAT_MASS = 30f,
      FAT_MASS = 35f,
      VERY_FAT_MASS = 40f;
}

/**
 * Класс человека
 */
public class Human implements Constants {

  /**
   * Для приведения чисел к нужному формату
   */
  private final DecimalFormat decimalFormat = new DecimalFormat("#.###");
  /**
   * День рождения человека
   */
  private int bDay;
  /**
   * Месяц рождения человека
   */
  private int bMonth;
  /**
   * Год рождения человека
   */
  private int bYear;
  /**
   * Рост человека
   */
  private float height;
  /**
   * Вес человека
   */
  private float weight;
  /**
   * Имя человека
   */
  private String name;
  /**
   * Фамилия человека
   */
  private String surname;
  /**
   * Пол человека
   */
  private boolean gender;
  /**
   * Флаг, активируемый при вводе пола в экземпляр класса
   */
  private boolean isGender;

  /**
   * Конструктор возвращающий пустой экземпляр класса
   */
  public Human() {

    this.isGender = false;
  }

  /**
   * Конструктор возвращаюший заполненный экземпляр класса
   *
   * @param bDay    День рождения
   * @param bMonth  Месяц рождения
   * @param bYear   Год рождения
   * @param height  Рост человека
   * @param weight  Вес человека
   * @param name    Имя человека
   * @param surname Фамилия человека
   * @param gender  Пол человека
   */
  public Human(int bDay, int bMonth, int bYear,
      float height, float weight,
      String name, String surname, boolean gender) {

    if (bDay > 0 &&
        bMonth > 0 &&
        bYear > 0 &&
        height > 0 &&
        weight > 0 &&
        (name != null) &&
        (surname != null)) {
      this.bDay = bDay;
      this.bMonth = bMonth;
      this.bYear = bYear;

      this.height = height;
      this.weight = weight;

      this.name = name;
      this.surname = surname;
      this.gender = gender;
      this.isGender = true;
    }

  }


  /**
   * Метод установки даты рождения
   *
   * @param date дата в формте ДД.ММ.ГГГГ
   * @return true при корректной дате false при некорректной
   */
  public boolean setBirthday(String date) {
    String pattern = "(0[1-9]|[12]\\d|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
    Pattern pat = Pattern.compile(pattern);
    Matcher match = pat.matcher(date);

    if (match.find()) {
      String[] dateList = date.split("\\.");
      this.bDay = Integer.parseInt(dateList[0]);
      this.bMonth = Integer.parseInt(dateList[1]);
      this.bYear = Integer.parseInt(dateList[2]);
      return true;
    }
    return false;
  }

  /**
   * Метод получения строкового представления даты рождения
   *
   * @return Дату рождения в формате ДД.ММ.ГГГГ
   */
  public String getBirthday() {
    return String.format("%02d.%02d.%d", this.bDay, this.bMonth, this.bYear);
  }

  /**
   * Метод получения имя и фамилии человека
   *
   * @return имя и фамилия через пробел
   */
  public String getFullName() {
    return String.format("%s %s", this.name, this.surname);
  }

  /**
   * Сеттер веса человека
   *
   * @param weight вес человека
   * @return true при корректном весе false при некорректном
   */
  public boolean setWeight(float weight) {
    if (weight <= 0) {
      return false;
    }
    this.weight = weight;
    return true;
  }

  /**
   * Геттер веса человека
   *
   * @return вес человека
   */
  public float getWeight() {
    return this.weight;
  }

  /**
   * Сеттер роста человека
   *
   * @param height рост человека
   * @return true при корректном росте false при некорректном
   */
  public boolean setHeight(float height) {
    if (height <= 0) {
      return false;
    }
    this.height = height;
    return true;
  }

  /**
   * Геттер роста человека
   *
   * @return рост человека
   */
  public float getHeight() {

    return this.height;
  }

  /**
   * Метод получения даты рожденгия для компаратора
   *
   * @return Экземпляр Date содержащий дату рождения
   */
  public Date getBDate() {
    return new Date(this.bYear, this.bMonth, this.bDay);
  }

  /**
   * Сеттер имени человека
   *
   * @param name имя человека
   * @return true при корректном имени false при некорректном
   */
  public boolean setName(String name) {
    if (name == null) {
      return false;
    }
    this.name = name;
    return true;
  }

  /**
   * Геттер имени человека
   *
   * @return Имя человека
   */
  public String getName() {
    return this.name;
  }

  /**
   * Сеттер фамилии человека
   *
   * @param surname фамилия человека
   * @return true при корректной фамилии false при некорректной
   */
  public boolean setSurname(String surname) {
    if (surname == null) {
      return false;
    }
    this.surname = surname;
    return true;
  }

  /**
   * Геттер фамилии человека
   *
   * @return Фамилию человека
   */
  public String getSurname() {
    return this.surname;
  }

  /**
   * Сеттер пола человека
   *
   * @param gender, где ж - женский пол, м - мужской
   */
  public boolean setGender(String gender) {
    if (Objects.equals(gender.toLowerCase(), "м")) {
      this.gender = true;
      this.isGender = true;
    } else if (Objects.equals(gender.toLowerCase(), "ж")) {
      this.gender = false;
      this.isGender = true;
    } else {
      return false;
    }
    return true;
  }

  /**
   * Геттер пола человека
   *
   * @return Строку содержащую пол человека
   */
  public String getGender() {
    if (!this.isGender) {
      return "Нет данных";
    }
    if (this.gender) {
      return "Мужской";
    } else {
      return "Женский";
    }
  }

  /**
   * Метод получения ИМТ человека, рассчитываемый из роста и веса человека
   *
   * @return строку, содержащую вердикт о комплекции человека согласно ИМТ
   */
  public String getBMI() {
    if (this.weight == 0 || this.height == 0) {
      return "Недостаточно данных";
    }
    double BMI = this.weight / Math.pow(this.height, 2);
    if (BMI <= VERY_LOW_MASS) {
      return "Выраженный дефицит массы тела";
    } else if (BMI < LOW_MASS) {
      return "Недостаточная масса тела";
    } else if (BMI < NORM_MASS) {
      return "Норма";
    } else if (BMI < NEAR_TO_FAT_MASS) {
      return "Избыточная масса тела";
    } else if (BMI < FAT_MASS) {
      return "Ожирение 1 степени";
    } else if (BMI < VERY_FAT_MASS) {
      return "Ожирение 2 степени";
    } else {
      return "Ожирение 3 степени";
    }
  }

  /**
   * Метод строкового представления класса
   */
  @Override
  public String toString() {
    return String.format("""
            Имя: %s
            Фамилия: %s
            Дата рождения: %s
            Пол: %s
            Рост: %s
            Вес: %s
            Конфигурация тела,
            согласно ИМТ: %s
                        
            #########################
            """,
        this.getName(),
        this.getSurname(),
        this.getBirthday(),
        this.getGender(),
        this.decimalFormat.format(this.getHeight()),
        this.decimalFormat.format(this.getWeight()),
        this.getBMI());
  }
}