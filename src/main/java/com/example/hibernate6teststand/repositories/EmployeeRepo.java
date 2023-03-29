package com.example.hibernate6teststand.repositories;

import com.example.hibernate6teststand.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
    @Query("SELECT max(e.id) FROM Employee e where e.salary > 100")
    UUID getMaxUUID();

    @Query("SELECT secondMaxSalary()")
    Long getMaxSalary();
}
