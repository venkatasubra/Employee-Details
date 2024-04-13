package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.TaxDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Date;
import com.employee.entity.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/employees")
    public ResponseEntity<String> storeEmployeeDetails(@Valid @RequestBody EmployeeDTO employeeDTO) {
        // Validate and map DTO to entity
        Employee employee = employeeDTO.toEntity();

        // Save employee details
        entityManager.persist(employee);
        entityManager.flush();

        return ResponseEntity.ok("Employee details stored successfully");
    }

    @GetMapping("/employees/{employeeId}/tax")
    public ResponseEntity<TaxDetails> getEmployeeTaxDetails(@PathVariable String employeeId) {
        // Retrieve employee from database
        Employee employee = entityManager.find(Employee.class, employeeId);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Calculate tax details
        TaxDetails taxDetails = calculateTax(employee);

        return ResponseEntity.ok(taxDetails);
    }

    private TaxDetails calculateTax(Employee employee) {
        // Consider DOJ while calculating total salary
        Date today = new Date();
        Date doj = employee.getDoj();
        int monthsWorked = (int) ((today.getTime() - doj.getTime()) / (1000 * 60 * 60 * 24 * 30.44)); // Approximate months

        double totalSalary = employee.getSalary() * monthsWorked;
        double taxAmount = 0;

        if (totalSalary <= 250000) {
            // No tax
        } else if (totalSalary <= 500000) {
            taxAmount = (totalSalary - 250000) * 0.05;
        } else if (totalSalary <= 1000000) {
            taxAmount = 12500 + (totalSalary - 500000) * 0.1;
        } else {
            taxAmount = 62500 + (totalSalary - 1000000) * 0.2;
        }

        // Additional cess for salary over 2500000
        double cess = totalSalary > 2500000 ? (totalSalary - 2500000) * 0.02 : 0;

        return new TaxDetails(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                totalSalary, taxAmount, cess);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}