package edu.espe.springlab.service.impl;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentCreateRequest;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateRequest;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.StudentService;
import edu.espe.springlab.web.advice.ConflictException;
import edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo){
        this.repo = repo;
    }

    @Override
    public StudentResponse create(StudentCreateRequest request) {
        if(repo.existsByEmail(request.getEmail())){
            throw new ConflictException("El email ya esta registrado");
        }
        Student s = new Student();
        s.setFullName(request.getFullName());
        s.setEmail(request.getEmail());
        s.setBirthDate(request.getBirthDate());
        s.setActive(true);

        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse getById(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        return toResponse(s);
    }

    @Override
    public StudentResponse deactivate(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        s.setActive(false);
        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse reactivate(Long id){
        Student s = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        s.setActive(true);
        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse update(Long id, StudentUpdateRequest request) {
        Student s = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        Optional<Student> existing = repo.findByEmail(request.getEmail());
        if(existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new ConflictException("El email ya está registrado por otro estudiante");
        }

        s.setFullName(request.getFullName());
        s.setEmail(request.getEmail());
        s.setBirthDate(request.getBirthDate());
        s.setActive(request.getActive());

        return toResponse(repo.save(s));
    }

    @Override
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", repo.count());
        stats.put("active", repo.countByActive(true));
        stats.put("inactive", repo.countByActive(false));
        return stats;
    }

    // --- AQUÍ ESTÁ LA SOLUCIÓN DEL ERROR ---
    @Override
    public Page<StudentResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Override
    public Page<StudentResponse> searchByName(String name, Pageable pageable) {
        return repo.findByFullNameContainingIgnoreCase(name, pageable).map(this::toResponse);
    }

    /* Mapeo interno Entidad -> DTO de salida */
    private StudentResponse toResponse(Student s){
        StudentResponse r = new StudentResponse();
        r.setId(s.getId());
        r.setFullName(s.getFullName());
        r.setEmail(s.getEmail());
        r.setBirthDate(s.getBirthDate());
        r.setActive(s.isActive());
        return r;
    }
}