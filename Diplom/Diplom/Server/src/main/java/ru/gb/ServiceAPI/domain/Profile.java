package ru.gb.ServiceAPI.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * id - идентификатор, уникальный, обязательный к заполнению, присваевается автоматически
 * name - имя - обязательный к заполнению
 * birthdate - дата рождения, обязательная к заполнению
 * phoneNumber - номер телефона, обязательный к заполнению
 * extraPhoneNumber - дополнительный номер телефона
 * email - почта, обязательная к заполнению
 * extraEmail - дополнительная почта
 * login - логин, обязательный к заполнению
 * password - пароль, обязательный к заполнению
 */
@Data
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;


    /**
     * Метод возвращения возраста по дате рождения
     * @return - Возраст
     */
    public int getAge(){
        LocalDate date = LocalDate.parse(this.getBirthdate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int year = date.getYear();
        return 2024 - year;
    }
}
