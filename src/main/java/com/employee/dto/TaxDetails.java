package com.employee.dto;

public class TaxDetails {
    private String employeeId;
    private String firstName;
    private String lastName;
    private double yearlySalary;
    private double taxAmount;
    private double cessAmount;

    // Constructor

    public TaxDetails(String employeeId, String firstName, String lastName, double yearlySalary, double taxAmount, double cessAmount) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearlySalary = yearlySalary;
        this.taxAmount = taxAmount;
        this.cessAmount = cessAmount;
    }


    // Getters


    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getYearlySalary() {
        return yearlySalary;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getCessAmount() {
        return cessAmount;
    }
}
