package com.test.hibernate6customfunctions.repositories;

import com.test.hibernate6customfunctions.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
    // Second max salary
    @Query("select secondMaxSalary()")
    Double getSecondMaxSalaryCustom();

    @Query("select max(salary) from Employee where salary <> (select max(salary) from Employee)")
    Double getSecondMaxSalaryQuery();
    // ----------------------------------


    // Number of employees with salary greater than parameter using custom aggregate function
    @Query("select countItemsGreaterVal(salary, :edge) from Employee")
    Long countEmployeeSalaryGreaterCustom(double edge);

    @Query("select count(*) " +
            "filter (where salary > cast(:edge as bigdecimal)) " +
            "from Employee")
    Long countEmployeeSalaryGreaterQuery(double edge);
    // ----------------------------------


    // Number of employees with salary greater than parameter
    @Query("select countItemsGreaterVal(salary, :bottomEdge) filter (WHERE salary < :topEdge) from Employee")
    Long countEmployeeSalaryBetweenCustom(double bottomEdge, double topEdge);

    @Query("select " +
            "count(*) " +
            "from Employee " +
            "WHERE salary > cast(:bottomEdge as bigdecimal) and salary < cast(:topEdge as bigdecimal)")
    Long countEmployeeSalaryBetweenQuery(double bottomEdge, double topEdge);
    // ----------------------------------
}
