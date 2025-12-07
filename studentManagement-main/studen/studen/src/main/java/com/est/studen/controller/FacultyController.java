package com.est.studen.controller;

import com.est.studen.data.entity.Faculty;
import com.est.studen.data.repository.FacultyRepository;
import com.est.studen.data.repository.StudentRepository;
import com.est.studen.service.dto.FacultyDTO;
import com.est.studen.service.dto.StudentDTO;
import com.est.studen.service.mapper.FacultyMapper;
import com.est.studen.service.mapper.StudentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public String listFaculties(Model model) {
        List<FacultyDTO> faculties = facultyRepository.findAll()
                .stream()
                .map(FacultyMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("faculties", faculties);
        return "faculties/list";
    }

    @GetMapping("/new")
    public String createFacultyForm(Model model) {
        model.addAttribute("faculty", new FacultyDTO());
        return "faculties/form";
    }

    @GetMapping("/edit/{id}")
    public String editFacultyForm(@PathVariable Long id, Model model) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow();
        model.addAttribute("faculty", FacultyMapper.toDTO(faculty));
        return "faculties/form";
    }

    @PostMapping("/save")
    public String saveFaculty(
            @Valid @ModelAttribute("faculty") FacultyDTO facultyDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "faculties/form";
        }

        Faculty faculty;
        if (facultyDTO.getId() != null) {
            faculty = facultyRepository.findById(facultyDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));
        } else {
            faculty = new Faculty();
        }

        faculty.setFacultyName(facultyDTO.getFacultyName());
        facultyRepository.save(faculty);
        return "redirect:/faculties";
    }


    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable Long id) {
        facultyRepository.deleteById(id);
        return "redirect:/faculties";
    }

    @GetMapping("/{id}/students")
    public String listStudentsByFaculty(@PathVariable Long id, Model model) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));

        List<StudentDTO> students = studentRepository.findByFacultyId(id)
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("faculty", faculty);
        model.addAttribute("students", students);

        return "faculties/students";
    }

}
