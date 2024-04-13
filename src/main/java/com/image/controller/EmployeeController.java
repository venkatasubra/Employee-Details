package com.image.controller;

import com.image.entity.Employee;
import com.image.entity.TaxDeduction;
import com.image.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    private List<Employee> employees = new ArrayList<>();

    @PostMapping
    public String addEmployee(@RequestBody Employee employee) {
        // Validate employee data
        if (!isValidEmployee(employee)) {
            throw new IllegalArgumentException("Invalid employee data");
        }

        employeeRepository.save(employee);
        return "Employee added successfully";
    }

    @GetMapping("/tax-deduction")
    public List<TaxDeduction> getTaxDeductions() {
        // Calculate tax deductions for each employee
        List<TaxDeduction> deductions = new ArrayList<>();
        for (Employee employee : employees) {
            deductions.add(calculateTaxDeduction(employee));
        }
        return deductions;
    }

    // Helper method to validate employee data
    private boolean isValidEmployee(Employee employee) {
        // let's assume all fields are mandatory and not empty
        return employee.getEmployeeId() > 0 &&
                !employee.getFirstName().isEmpty() &&
                !employee.getLastName().isEmpty() &&
                !employee.getEmail().isEmpty() &&
                !employee.getPhoneNumbers().isEmpty() &&
                employee.getDoj() != null &&
                employee.getSalary() > 0;
    }

    // Helper method to calculate tax deduction for an employee
    private TaxDeduction calculateTaxDeduction(Employee employee) {
        // Implement tax calculation logic based on salary
        // For simplicity, let's assume tax slabs as mentioned in the requirement
        double yearlySalary = calculateYearlySalary(employee);
        double taxAmount = calculateTaxAmount(yearlySalary);
        double cessAmount = calculateCessAmount(yearlySalary);
        return new TaxDeduction(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                yearlySalary, taxAmount, cessAmount);
    }

    // Helper method to calculate yearly salary based on DOJ
    private double calculateYearlySalary(Employee employee) {
       // let's assume total months of service
        int monthsOfService = LocalDate.now().getMonthValue() - employee.getDoj().getMonthValue() + 1;
        return employee.getSalary() * monthsOfService;
    }

    // Helper method to calculate tax amount based on yearly salary
    private double calculateTaxAmount(double yearlySalary) {
         // let's assume the tax slabs mentioned in the requirement
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return 12500 + (yearlySalary - 500000) * 0.1;
        } else {
            return 62500 + (yearlySalary - 1000000) * 0.2;
        }
    }

    // Helper method to calculate cess amount based on yearly salary
    private double calculateCessAmount(double yearlySalary) {
        // let's assume 2% cess for yearly salary over 2500000
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        } else {
            return 0;
        }
    }
}
