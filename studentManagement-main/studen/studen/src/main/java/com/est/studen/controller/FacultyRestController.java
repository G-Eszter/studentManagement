package com.est.studen.controller;

import com.est.studen.data.entity.Faculty;
import com.est.studen.data.repository.FacultyRepository;
import com.est.studen.service.FacultyService;
import com.est.studen.service.dto.FacultyDTO;
import com.est.studen.service.mapper.FacultyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/faculties")
public class FacultyRestController {

    private final FacultyService service;
    @Autowired
    private final FacultyRepository repository;

    public FacultyRestController(FacultyService service, FacultyRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public List<FacultyDTO> getAllFaculties() {
        return repository.findAll()
                .stream()
                .map(FacultyMapper::toDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public Faculty getById(@PathVariable Long id) {
        return service.getFaculty(id);
    }

    @PostMapping
    public FacultyDTO create(@RequestBody FacultyDTO facultyDTO) {
        Faculty faculty = FacultyMapper.toEntity(facultyDTO);
        Faculty savedFaculty = repository.save(faculty);
        return FacultyMapper.toDTO(savedFaculty);
    }

    @PutMapping("/{id}")
    public Faculty update(@PathVariable Long id, @RequestBody Faculty faculty) {
        faculty.setId(id);
        return service.saveFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteFaculty(id);
    }
}
