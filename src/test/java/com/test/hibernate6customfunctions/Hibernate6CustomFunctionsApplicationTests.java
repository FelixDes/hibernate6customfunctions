package com.test.hibernate6customfunctions;

import com.test.hibernate6customfunctions.repositories.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class Hibernate6CustomFunctionsApplicationTests {
    @Autowired
    EmployeeRepo employeeRepo;

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
}
