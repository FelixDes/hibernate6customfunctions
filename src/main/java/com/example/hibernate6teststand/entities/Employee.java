package com.example.hibernate6teststand.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Employee {
    @Id
    private Long id;
    private Long salary;
    private String name;
    private String surname;
}
