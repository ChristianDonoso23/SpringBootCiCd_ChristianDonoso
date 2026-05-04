package edu.espe.springlab.web.controller;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentCreateRequest;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateRequest;
import edu.espe.springlab.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    /*Inyección de dependencia*/
    private final StudentService service;

    public StudentController(StudentService service){
        this.service = service;
    }

    /*Crear un estudiante*/
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    /*Obtener un estudiante por Id*/
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    /*Obtener todos los estudiantes*/
    //@GetMapping
    //public ResponseEntity<List<StudentResponse>> getAllStudents(){
        //return ResponseEntity.ok(service.list());
    //}

    /*Desactivar estudiante*/
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<StudentResponse> deactivateStudent(@PathVariable Long id){
        return ResponseEntity.ok(service.deactivate(id));
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<StudentResponse> reactivateStudent(@PathVariable Long id){
        return ResponseEntity.ok(service.reactivate(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats(){
        return ResponseEntity.ok(service.getStats());
    }

    /*Obtener todos los estudiantes o buscar por nombre*/
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (name != null && !name.trim().isEmpty()) {
            return ResponseEntity.ok(service.searchByName(name, pageable));
        }
        return ResponseEntity.ok(service.list(pageable));
    }
}
