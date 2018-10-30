package com.sanver.trials.simplerestservicewithmysqltdd.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class StudentDTO {
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Past
    private LocalDate birthDate;

    private int age;

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public StudentDTO() {
    }

    public StudentDTO(@NotNull @Size(min = 3, max = 100) String name, @NotNull @Past LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
}
