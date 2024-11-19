package com.example.employeemanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message = "Cannot be null")
    @Size(min =3 , message = "Length must be more than 2 characters")
    private String ID;

    @NotEmpty(message = "Cannot be null.")
    @Size(min = 5, message = "Length must be more than 4 characters.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters")
    private String name;

    @NotEmpty(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "Age cannot be null")
    @Min(value = 26, message = "Age must be more than 25")
    @Positive(message = "must be positive")
    private int age;

    @NotEmpty(message = "Position cannot be null")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator'")
    private String position;

    private boolean onLeave=false;

    @NotNull(message = "Hire date cannot be null")
    @PastOrPresent(message = "Hire date must be in the present or past")
    private LocalDate hireDate;

    @NotNull(message = "Annual leave cannot be null")
    @Positive(message = "Annual leave must be a positive number")
    private int annualLeave;
}
