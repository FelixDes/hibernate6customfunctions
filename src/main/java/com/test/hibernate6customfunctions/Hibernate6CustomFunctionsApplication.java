package com.test.hibernate6customfunctions;

import com.test.hibernate6customfunctions.entities.Employee;
import com.test.hibernate6customfunctions.repositories.EmployeeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;


@SpringBootApplication
public class Hibernate6CustomFunctionsApplication implements CommandLineRunner {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    EntityManager em;

    public static void main(String[] args) {
        SpringApplication.run(Hibernate6CustomFunctionsApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
