package com.react.sachin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.sachin.Entity.Employee;
import com.react.sachin.Repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id,
                                   Employee employee) {

        employee.setId(id);

        return repository.save(employee);

    }

    @Override
    public void deleteEmployee(Long id) {

        repository.deleteById(id);

    }

    @Override
    public Employee getEmployeeById(Long id) {

        return repository.findById(id).orElse(null);

    }

}