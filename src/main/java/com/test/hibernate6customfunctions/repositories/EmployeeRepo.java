package com.test.hibernate6customfunctions.repositories;

import com.test.hibernate6customfunctions.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
    @Query("SELECT max(e.id) filter (where e.salary > 350) FROM Employee e where e.salary > 100")
    UUID getMaxUUID();

    @Query("select secondMaxSalary()")
    Long getSecondMaxSalary();

    @Query("select countItemsGreaterVal(salary, :#{#edge}) from Employee")
    Long countEmployeeWithSalaryGreaterVal(@Param("edge") double edge);

    @Query("select countItemsGreaterVal(salary, :#{#bottomEdge}) filter (WHERE salary < :#{#topEdge}) from Employee")
    Long countEmployeeWithSalaryGreaterValAndLessThanFilter(@Param("bottomEdge") double bottomEdge, @Param("topEdge") double topEdge);
}
