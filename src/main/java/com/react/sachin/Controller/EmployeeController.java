package com.react.sachin.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.sachin.Entity.Employee;
import com.react.sachin.Service.EmployeeService;

@RestController
@RequestMapping("/employees")
//@CrossOrigin(origins = "http://localhost:5173") //beceaue we configured in security config cor configration 
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public List<Employee> getAllEmployees() {
         System.out.println("--- in query annoation for all");
        return service.getAllEmployees();
    }

    //@QueryMa{{pping("/{id}") Not working in spring boot
  @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
       System.out.println("--- in query annoation");
        return service.getEmployeeById(id);
    }

    @PostMapping
    public Employee saveEmployee( @RequestBody Employee employee) {

System.out.println("---------------------------------------in save");                
        return service.saveEmployee(employee);

    }

    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {

        return service.updateEmployee(id, employee);

    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        service.deleteEmployee(id);

        return "Employee Deleted Successfully";

    }

}