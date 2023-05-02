package com.test.hibernate6customfunctions;

import com.test.hibernate6customfunctions.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hibernate6CustomFunctionsApplication implements CommandLineRunner {

    @Autowired
    EmployeeRepo er;

    public static void main(String[] args) {
        SpringApplication.run(Hibernate6CustomFunctionsApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        System.out.println(er.countEmployeeWithSalaryBetweenValAndFilterCustom(35.9, 300));
//        System.out.println(er.countEmployeeWithSalaryBetweenValAndFilterQuery(35.9, 300));
        System.out.println(er.getSecondMaxSalaryCustom());
        System.out.println(er.getSecondMaxSalaryQuery());
    }
}
