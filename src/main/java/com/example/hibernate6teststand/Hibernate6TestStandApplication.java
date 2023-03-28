package com.example.hibernate6teststand;

import com.example.hibernate6teststand.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hibernate6TestStandApplication implements CommandLineRunner {

    @Autowired
    EmployeeRepo er;

    public static void main(String[] args) {
        SpringApplication.run(Hibernate6TestStandApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        System.out.println(er.getS());
        System.out.println(er.getM());
    }
}
