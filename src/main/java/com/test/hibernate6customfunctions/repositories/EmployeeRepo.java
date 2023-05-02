package com.test.hibernate6customfunctions.repositories;

import com.test.hibernate6customfunctions.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
//    @Query("SELECT max(e.id) filter (where e.salary > 350) FROM Employee e where e.salary > 100")
//    UUID getMaxUUID();

    // Second max salary ----------------
    @Query("select secondMaxSalary()")
    BigDecimal getSecondMaxSalaryCustom();

    @Query("select max(salary) from Employee where salary <> (select max(salary) from Employee)")
    BigDecimal getSecondMaxSalaryQuery();
    // ----------------------------------


    // Number of employees with salary greater than parameter using custom aggregate function
    @Query("select countItemsGreaterVal(salary, :#{#edge}) from Employee")
    Long countEmployeeWithSalaryGreaterValCustom(@Param("edge") double edge);

    @Query("select count(*) filter (where salary > cast(:#{#edge} as bigdecimal)) from Employee")
    Long countEmployeeWithSalaryGreaterValQuery(@Param("edge") double edge);
    // ----------------------------------


    // Number of employees with salary greater than parameter
    @Query("select countItemsGreaterVal(salary, :#{#bottomEdge}) filter (WHERE salary <:#{#topEdge}) from Employee")
    Long countEmployeeWithSalaryBetweenValAndFilterCustom(@Param("bottomEdge") double bottomEdge, @Param("topEdge") double topEdge);

    @Query("select count(*) from Employee WHERE salary > cast(:#{#bottomEdge} as bigdecimal) and salary < cast(:#{#topEdge} as bigdecimal)")
    Long countEmployeeWithSalaryBetweenValAndFilterQuery(@Param("bottomEdge") double bottomEdge, @Param("topEdge") double topEdge);

    // ----------------------------------
}
