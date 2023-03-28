package com.example.hibernate6teststand.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
public class Employee {
    @Id
    @UuidGenerator
    private UUID id;
    private Long salary;
    private String name;
    private String surname;
}
