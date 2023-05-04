package com.test.hibernate6customfunctions;

import com.test.hibernate6customfunctions.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Hibernate6CustomFunctionsApplication implements CommandLineRunner {
    @Autowired
    EmployeeRepo employeeRepo;

    public static void main(String[] args) {
        SpringApplication.run(Hibernate6CustomFunctionsApplication.class, args);
    }

    @Override
    public void run(String... args) {}
}
