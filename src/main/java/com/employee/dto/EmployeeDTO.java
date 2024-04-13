package com.employee.dto;


import com.employee.entity.Employee;
import jakarta.validation.constraints.Size;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class EmployeeDTO {
    @NotBlank
    private String employeeId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @Size(min = 1)
    private List<@NotBlank String> phoneNumbers;

    @NotNull
    @Past
    private Date doj; // Date of Joining

    @Positive
    private double salary;

    public Employee toEntity() {
        Employee employee = new Employee();
        employee.setEmployeeId(this.employeeId);
        employee.setFirstName(this.firstName);
        employee.setLastName(this.lastName);
        employee.setEmail(this.email);
        employee.setPhoneNumbers(this.phoneNumbers);
        employee.setDoj(this.doj);
        employee.setSalary(this.salary);
        return employee;
    }

    // Getters and setters
    // No-args constructor
}