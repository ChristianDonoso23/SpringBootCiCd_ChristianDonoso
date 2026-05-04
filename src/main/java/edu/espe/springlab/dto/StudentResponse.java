package edu.espe.springlab.dto;

import java.time.LocalDate;

public class StudentResponse {
    private Long id;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
