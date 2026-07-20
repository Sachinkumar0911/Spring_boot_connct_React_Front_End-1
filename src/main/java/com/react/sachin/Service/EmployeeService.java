package com.react.sachin.Service;

import java.util.List;

import com.react.sachin.Entity.Employee;

public interface  EmployeeService {
    List<Employee> getAllEmployees();

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

}
