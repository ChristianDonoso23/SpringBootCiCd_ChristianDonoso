package edu.espe.springlab.service;

import edu.espe.springlab.dto.StudentCreateRequest;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface StudentService {
    StudentResponse create(StudentCreateRequest request);
    StudentResponse getById(Long id);
    StudentResponse deactivate(Long id);
    StudentResponse reactivate(Long id);
    StudentResponse update(Long id, StudentUpdateRequest request);
    Map<String, Long> getStats();

    // Métodos con Paginación correctos
    Page<StudentResponse> list(Pageable pageable);
    Page<StudentResponse> searchByName(String name, Pageable pageable);
}