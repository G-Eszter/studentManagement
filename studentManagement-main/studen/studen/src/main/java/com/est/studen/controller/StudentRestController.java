package com.est.studen.controller;

import com.est.studen.data.entity.Student;
import com.est.studen.data.repository.StudentRepository;
import com.est.studen.service.StudentService;
import com.est.studen.service.dto.StudentDTO;
import com.est.studen.service.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService service;
    @Autowired
    private final StudentRepository repository;

    public StudentRestController(StudentService service, StudentRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public List<StudentDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return service.getStudent(id);
    }

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = StudentMapper.toEntity(studentDTO);
        Student savedStudent = repository.save(student);
        return StudentMapper.toDto(savedStudent);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return service.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteStudent(id);
    }
}
