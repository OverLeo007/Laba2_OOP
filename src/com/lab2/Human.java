package com.lab2;


import java.text.DecimalFormat;

/**
 * Класс человека
 */
public class Human {

  /**
   * Для приведения чисел к нужному формату
   */
  private final DecimalFormat decimalFormat = new DecimalFormat("#.###");

  /**
   * День, месяц, год рождения человека
   */
  private int bDay, bMonth, bYear;

  /**
   * Рост и вес человека
   */
  private float height, weight;

  /**
   * Имя и фамилия человека
   */
  private String name, surname;

  /**
   * Пол человека
   */
  private boolean gender;

  /**
   * Флаг, активируемый при вводе пола в экземпляр класса
   */
  private boolean is_gender;

  /**
   * Конструктор возвращающий пустой экземпляр класса
   */
  public Human() {

    this.is_gender = false;
  }

  /**
   * Конструктор возвращаюший заполненный экземпляр класса
   *
   * @param bDay День рождения
   * @param bMonth Месяц рождения
   * @param bYear Год рождения
   * @param height Рост человека
   * @param weight Вес человека
   * @param name Имя человека
   * @param surname Фамилия человека
   * @param gender Пол человека
   */
  public Human(int bDay, int bMonth, int bYear,
      float height, float weight,
      String name, String surname, boolean gender) {

    this.bDay = bDay;
    this.bMonth = bMonth;
    this.bYear = bYear;

    this.height = height;
    this.weight = weight;

    this.name = name;
    this.surname = surname;
    this.gender = gender;
    this.is_gender = true;
  }


  /**
   * Сеттер даты рождения
   *
   * @param bDay День рождения
   * @param bMonth Месяц рождения
   * @param bYear Год рождения
   */
  public void setBirthday(int bDay, int bMonth, int bYear) {
    this.bDay = bDay;
    this.bMonth = bMonth;
    this.bYear = bYear;
  }

  /**
   * Геттер даты рождения
   *
   * @return Дату рождения в формате ДД.ММ.ГГГГ
   */
  public String getBirthday() {
    return String.format("%02d.%02d.%d", this.bDay, this.bMonth, this.bYear);
  }

  /**
   * Геттер ИФ человека
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
   */
  public void setWeight(float weight) {
    this.weight = weight;
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
   */
  public void setHeight(float height) {
    this.height = height;
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
   * Геттер даты рожденгия в днях
   *
   * @return int дней прошедших от 0 года до рождения
   */
  public int getBDateInDays(){
    return this.bYear * 355 + this.bMonth * 30 + this.bDay;
  }

  /**
   * Сеттер имени человека
   *
   * @param name имя человека
   */
  public void setName(String name) {
    this.name = name;
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
   */
  public void setSurname(String surname) {
    this.surname = surname;
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
   * @param gender, где 0 - женский пол, 1 - мужской
   */
  public void setGender(boolean gender) {
    this.is_gender = true;
    this.gender = gender;
  }

  /**
   * Геттер пола человека
   *
   * @return Строку содержащую пол человека
   */
  public String getGender() {
    if (!this.is_gender){
      return "Нет данных";
    }
    if (this.gender) {
      return "Мужской";
    } else {
      return "Женский";
    }
  }

  /**
   * Геттер ИМТ человека, рассчитываемый из роста и веса человека
   *
   * @return строку, содержащую вердикт о комплекции человека согласно ИМТ
   */
  public String getBMI() {
    if ( this.weight == 0 || this.height == 0) return "Недостаточно данных";
    double BMI = this.weight / Math.pow(this.height, 2);
    if (BMI <= 16) {
      return "Выраженный дефицит массы тела";
    } else if (BMI < 18.5) {
      return "Недостаточная масса тела";
    } else if (BMI < 25) {
      return "Норма";
    } else if (BMI < 30) {
      return "Избыточная масса тела";
    } else if (BMI < 35) {
      return "Ожирение 1 степени";
    } else if (BMI < 40) {
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
