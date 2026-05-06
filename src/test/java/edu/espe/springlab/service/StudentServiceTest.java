package edu.espe.springlab.service;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentCreateRequest;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.impl.StudentServiceImpl;
import edu.espe.springlab.web.advice.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({StudentServiceImpl.class})
public class StudentServiceTest {

    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldNotAllowDuplicatedEmail() {
        // Arrange (Preparar el escenario)
        String email = "duplicate@example.com";

        Student existing = new Student();
        existing.setFullName("Existing User");
        existing.setEmail(email);
        existing.setBirthDate(LocalDate.of(2001, 12, 1));
        existing.setActive(true);
        repository.save(existing);

        StudentCreateRequest req = new StudentCreateRequest();
        req.setFullName("New user");
        req.setEmail(email);
        req.setBirthDate(LocalDate.of(2001, 12, 1));

        // Act & Assert (Ejecutar y Verificar)
        // Verificamos que al intentar crear, se lance la excepción de conflicto
        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Email already exists"); // Opcional: verifica el mensaje si tu lógica lo incluye
    }
}