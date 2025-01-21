package com.example.dems.models;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
/**
 * The Data class represents an employee entity with various attributes such as
 * name, designation, department, joining date, salary, and skills. It is
 * annotated as an Entity and mapped to the "employees" table in the database.
 *
 * <p>
 * Attributes:
 * <ul>
 * <li>id: The unique identifier for the employee. It is auto-generated.</li>
 * <li>name: The name of the employee. Cannot be blank.</li>
 * <li>designation: The designation of the employee. Cannot be blank.</li>
 * <li>department: The department to which the employee belongs. Cannot be blank.</li>
 * <li>joiningDate: The date when the employee joined. Cannot be null.</li>
 * <li>salary: The salary of the employee. Cannot be null.</li>
 * <li>skills: The skills of the employee stored as a JSON object.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The class implements Serializable to allow instances to be serialized.
 * </p>
 */
@Entity
@Table(name = "employees")
public class Data implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotBlank(message = "Designation can not be blank")
    private String designation;
    @NotBlank(message = "Department can not be blank")
    private String department;


    @NotNull(message = "Joining date can not be null")
    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @NotNull(message = "Salary can not be null")
    private Double salary;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> skills;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Map<String, Object> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Object> skills) {
        this.skills = skills;
    }
}
