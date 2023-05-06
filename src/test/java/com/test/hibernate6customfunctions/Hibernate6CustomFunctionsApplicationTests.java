package com.test.hibernate6customfunctions;

import com.test.hibernate6customfunctions.repositories.CriteriaRepo;
import com.test.hibernate6customfunctions.repositories.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@Transactional
class Hibernate6CustomFunctionsApplicationTests {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    CriteriaRepo criteriaRepo;

    public static PostgreSQLContainer<?> postgreSQLContainer;

    // Test containers setup
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
    // ----------------------------------

    // Tests
    @Test
    void runNormalCustomFunction() {
        var secondMaxFrom_Query = employeeRepo.getSecondMaxSalaryQuery();

        var secondMaxFrom_Custom = employeeRepo.getSecondMaxSalaryCustom();
        var secondMaxFrom_Criteria = criteriaRepo.getSecondMaxSalary();

        Assertions.assertEquals(secondMaxFrom_Custom, secondMaxFrom_Query);
        Assertions.assertEquals(secondMaxFrom_Criteria, secondMaxFrom_Query);
    }

    @Test
    void runAggregateCustomFunction() {
        var employeesWithSalaryGreater_Query = employeeRepo.countEmployeeSalaryGreaterQuery(350);

        var employeesWithSalaryGreater_Custom = employeeRepo.countEmployeeSalaryGreaterCustom(350);
        var employeesWithSalaryGreater_Criteria = criteriaRepo.employeesWithSalaryGreater(350);

        Assertions.assertEquals(employeesWithSalaryGreater_Custom, employeesWithSalaryGreater_Query);
        Assertions.assertEquals(employeesWithSalaryGreater_Criteria, employeesWithSalaryGreater_Query);
    }

    @Test
    void runAggregateCustomFunctionWithFilter() {
        var countOfSalaryBetweenParams_Query = employeeRepo.countEmployeeSalaryBetweenQuery(300, 400);

        var countOfSalaryBetweenParams_Custom = employeeRepo.countEmployeeSalaryBetweenCustom(300, 400);
        var countOfSalaryBetweenParams_Criteria = criteriaRepo.countEmployeeSalaryBetween(300, 400);

        Assertions.assertEquals(countOfSalaryBetweenParams_Custom, countOfSalaryBetweenParams_Query);
        Assertions.assertEquals(countOfSalaryBetweenParams_Criteria, countOfSalaryBetweenParams_Query);
    }
    // ----------------------------------

    @AfterAll
    public static void afterAll() {
        postgreSQLContainer.stop();
    }
}
