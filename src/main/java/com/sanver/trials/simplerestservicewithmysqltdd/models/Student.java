package com.sanver.trials.simplerestservicewithmysqltdd.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", initialValue = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Past
    private LocalDate birthDate;

    @Formula("case when year(now())=year(birth_Date) then 0 else (year(now())-year(birth_Date)-case when month(now())<month(birth_Date) then 1 " +
            "else (case when day(now())<day(birth_Date) then 1 else 0 end ) end) end")
    private int age;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Student() {
    }

    public Student(@NotNull @Size(min = 3, max = 100) String name, @NotNull @Past LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
}
