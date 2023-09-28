package ru.nikitakuts.menu.vacancy;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class Vacancy {
    private final Long id;


    @NotNull(message = "salary is required")
    @Positive(message = "salary must be positive")
    private final Long salary;


    @NotNull(message = "workExperience is required")
    @Positive(message = "workExperience must be zero or more years")
    private final Integer workExperience;


    @NotNull(message = "city is required")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "city must be a string")
    private final String city;


    @NotNull(message = "date is required")
    @URL(message = "date must be date now")
    private final Date date;

    public Vacancy(
            Long id,
            Long salary,
            Integer workExperience,
            String city,
            Date date
    ) {
        this.id = id;
        this.salary = salary;
        this.workExperience = workExperience;
        this.city = city;
        this.date = date;
    }

    @Id
    public Long getId() {
        return id;
    }

    public Long getSalary() {
        return salary;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public String getCity() {
        return city;
    }

    public Date getDate() {
        return date;
    }

    public Vacancy updateWith(Vacancy vacancy) {
        return new Vacancy(
                this.id,
                vacancy.salary,
                vacancy.workExperience,
                vacancy.city,
                vacancy.date
        );
    }
    
}
