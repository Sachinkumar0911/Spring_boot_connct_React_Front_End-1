package com.react.sachin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.react.sachin.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    
}