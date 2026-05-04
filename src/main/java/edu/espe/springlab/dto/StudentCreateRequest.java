package edu.espe.springlab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class StudentCreateRequest {
    @NotBlank @Size(min = 3, max = 120)
    private String fullName;

    @NotBlank @Email @Size(max = 120)
    private String email;

    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    private LocalDate birthDate;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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
}
