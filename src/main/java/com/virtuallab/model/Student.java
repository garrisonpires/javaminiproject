package com.virtuallab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String rollNo;

    @Column(nullable = false, length = 150)
    private String name;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Student() {
    }

    public Student(String rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
