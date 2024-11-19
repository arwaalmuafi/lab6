package com.example.employeemanagementsystem.Controller;

import com.example.employeemanagementsystem.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/lab6/v1/mangeSystem")
public class EmployeeController {


    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    @PostMapping("/add")
    public ResponseEntity employees(@RequestBody @Valid Employee employee, Errors errors) {

        if (errors.hasErrors()) {
            String massage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(massage);
        }
        employees.add(employee);

        return ResponseEntity.status(200).body(employees);
    }

    @PutMapping("/update/{ID}")
    public ResponseEntity updateEmployee(@PathVariable String ID, @Valid @RequestBody Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        int index = -10;
        for (Employee employee1 : employees) {
            if (employee1.getID().equals(ID)) {
                index = employees.indexOf(employee1);
            }
        }
        if (index == -10) {
            return ResponseEntity.status(400).body("not found");
        }
        employees.set(index, employee);

        return ResponseEntity.status(200).body("employee update");
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity deleteEmployee(@PathVariable String ID) {
        int index = -10;
        for (Employee employee1 : employees) {
            if (employee1.getID().equals(ID)) {
                index = employees.indexOf(employee1);
            }
        }
        if (index == -10) {
            return ResponseEntity.status(400).body("not found");
        }
        employees.remove(index);

        return ResponseEntity.status(200).body("Employee deleted");
    }

    @GetMapping("/get/{pos}")
    public ResponseEntity byPosition(@PathVariable String pos) {
        ArrayList<Employee> employeesByPosition = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().equalsIgnoreCase(pos)) {
                employeesByPosition.add(employee);
                return ResponseEntity.status(400).body(employeesByPosition);
            }
        }
        return ResponseEntity.status(200).body(employeesByPosition);
    }

    @GetMapping("/get/range")
    public ResponseEntity byAgeRange() {
        ArrayList<Employee> ages = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() > 26 && employee.getAge() <= 30) {
                ages.add(employee);
            }

        }
        return ResponseEntity.status(200).body(ages);
    }

    @GetMapping("/annualLeave/{ID}")
    public ResponseEntity annualLeave(@PathVariable String ID) {
        for (Employee employee : employees) {
            if (employee.isOnLeave()) {
                return ResponseEntity.status(400).body("Employee is already on leave");
            }
            if (employee.getAnnualLeave() == 0) {
                return ResponseEntity.status(400).body("No annual leave remaining");
            }
            employee.setOnLeave(true);
            employee.setAnnualLeave((employee.getAnnualLeave() - 1));
            return ResponseEntity.status(200).body("Leave applied successfully");
        }
        return ResponseEntity.status(400).body("annual leave is added");


    }
@GetMapping("/annual")
    public ResponseEntity noAnnualLeave(){
        ArrayList<Employee> noAnLeave=new ArrayList<>();
        for (Employee employee: employees){
            if(employee.getAnnualLeave()<1){
                noAnLeave.add(employee);
            }
            return ResponseEntity.status(200).body(noAnLeave);
        }
        return ResponseEntity.status(400).body("employees have annual leave");

    }


    @PostMapping("/promote/{ID}")
    public ResponseEntity promoteEmployee(@PathVariable String ID) {
        Employee employeeToPromote = null;
        Employee requester = null;

        for (Employee employee : employees) {
            if (employee.getID().equals(ID)) {
                employeeToPromote = employee;
            }
            if (employee.getID().equals(ID)) {
                requester = employee;
            }
        }

        if (employeeToPromote == null) {
            return ResponseEntity.status(400).body("Error: Employee with ID " + ID + " not found.");
        }
        if (!requester.getPosition().equalsIgnoreCase("supervisor")) {
            return ResponseEntity.status(400).body("Error: Requester is not authorized to promote employees.");
        }

        if (employeeToPromote.getAge() < 30) {
            return ResponseEntity.status(400).body("Error: Employee must be at least 30 years old to be promoted.");
        }
        if (employeeToPromote.isOnLeave()) {
            return ResponseEntity.status(400).body("Error: Employee cannot be promoted while on leave.");
        }
        employeeToPromote.setPosition("supervisor");

        return ResponseEntity.status(200).body("Success: Employee with ID " + ID + " has been promoted to supervisor.");
    }

    }



