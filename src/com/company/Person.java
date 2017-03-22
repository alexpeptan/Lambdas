package com.company;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by APEPTAN on 3/21/2017.
 */
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;

    public Sex getGender() {
        return gender;
    }

    Sex gender;

    public String getEmailAddress() {
        return emailAddress;
    }

    String emailAddress;

    public Person(String name, LocalDate bday, Sex gender, String mail){
        this.name = name;
        this.birthday = bday;
        this.gender = gender;
        this.emailAddress = mail;
    }

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - birthday.getYear();
    }

    public void printPerson() {
        System.out.println(this);
    }

    public String toString(){
        return name + " " + gender + " " + birthday + " " + emailAddress;
    }
}
