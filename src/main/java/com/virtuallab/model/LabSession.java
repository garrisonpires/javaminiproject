package com.virtuallab.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "lab_sessions")
public class LabSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String academicYear;

    @Column(nullable = false, length = 100)
    private String subjectName;

    @Column(nullable = false, length = 100)
    private String labName;

    @Column(nullable = false, length = 100)
    private String facultyInCharge;

    @Column(nullable = false)
    private LocalDate dateOfConduct;

    @Column(nullable = false)
    private Integer numberOfStudents;

    @Column(nullable = false, length = 20)
    private String status; // "Completed" or "Ongoing"

    @Column(length = 100)
    private String department;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "lab_session_id")
    private List<Student> students = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────────

    public LabSession() {
    }

    public LabSession(String academicYear, String subjectName, String labName,
            String facultyInCharge, LocalDate dateOfConduct,
            Integer numberOfStudents, String status, String department) {
        this.academicYear = academicYear;
        this.subjectName = subjectName;
        this.labName = labName;
        this.facultyInCharge = facultyInCharge;
        this.dateOfConduct = dateOfConduct;
        this.numberOfStudents = numberOfStudents;
        this.status = status;
        this.department = department;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getFacultyInCharge() {
        return facultyInCharge;
    }

    public void setFacultyInCharge(String facultyInCharge) {
        this.facultyInCharge = facultyInCharge;
    }

    public LocalDate getDateOfConduct() {
        return dateOfConduct;
    }

    public void setDateOfConduct(LocalDate dateOfConduct) {
        this.dateOfConduct = dateOfConduct;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
