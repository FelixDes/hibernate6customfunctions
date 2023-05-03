package com.test.hibernate6customfunctions;

import com.test.hibernate6customfunctions.repositories.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
@Transactional
class Hibernate6CustomFunctionsApplicationTests {
    @Autowired
    EmployeeRepo employeeRepo;
    public static PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void runNormalCustomFunction() {
        var secondMaxFrom_Custom = employeeRepo.getSecondMaxSalaryCustom();
        var secondMaxFrom_Query = employeeRepo.getSecondMaxSalaryQuery();

        Assertions.assertEquals(secondMaxFrom_Custom, secondMaxFrom_Query);
    }

    @Test
    void runAggregateCustomFunction() {
        var employeesWithSalaryGreaterThanVal_Custom = employeeRepo.countEmployeeWithSalaryGreaterValCustom(350);
        var employeesWithSalaryGreaterThanVal_Query = employeeRepo.countEmployeeWithSalaryGreaterValQuery(350);

        Assertions.assertEquals(employeesWithSalaryGreaterThanVal_Custom, employeesWithSalaryGreaterThanVal_Query);
    }

    @Test
    void runAggregateCustomFunctionWithFilter() {
        var countOfSalaryBetweenParams_Custom = employeeRepo.countEmployeeWithSalaryBetweenValAndFilterCustom(300, 400);
        var countOfSalaryBetweenParams_Query = employeeRepo.countEmployeeWithSalaryBetweenValAndFilterQuery(300, 400);

        Assertions.assertEquals(countOfSalaryBetweenParams_Custom, countOfSalaryBetweenParams_Query);
    }

    @AfterAll
    public static void afterAll() {
        postgreSQLContainer.stop();
    }
}
