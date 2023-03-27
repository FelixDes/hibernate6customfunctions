package com.example.hibernate6teststand.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private Long salary;
    private String name;
    private String surname;
}
