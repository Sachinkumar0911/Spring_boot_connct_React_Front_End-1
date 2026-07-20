package com.react.sachin.Entity;

// 1. Added the required imports
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="employee")
public class Employee {

    @Id // 2. Fixed typo: Capitalized the 'I' in @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private Double salary;
    private String mobile;
        private String email;



    // Getters and Setters
}
