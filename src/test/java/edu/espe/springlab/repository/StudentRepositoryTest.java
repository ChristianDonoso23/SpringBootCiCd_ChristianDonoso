package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void shouldSaveAndFindStudentByEmail() {
        // 1. Preparación
        Student student = new Student();
        student.setFullName("Test user");
        student.setEmail("test@example.com");
        student.setBirthDate(LocalDate.of(2001, 12, 1));
        student.setActive(true);

        // 2. Ejecución
        studentRepository.save(student);
        var result = studentRepository.findByEmail("test@example.com");

        // 3. Verificaciones (Aserciones)
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Test user");
    }
}
