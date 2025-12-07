package com.est.studen.controller;

import com.est.studen.data.entity.Faculty;
import com.est.studen.data.entity.Student;
import com.est.studen.data.repository.FacultyRepository;
import com.est.studen.data.repository.StudentRepository;
import com.est.studen.service.dto.StudentDTO;
import com.est.studen.service.mapper.StudentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @GetMapping
    public String listStudents(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Student> students;

        if (search != null && !search.isEmpty()) {
            students = studentRepository.searchByName(search);
        } else {
            students = studentRepository.findAll();
        }

        model.addAttribute("students", students);
        model.addAttribute("search", search);
        return "students/list";
    }

    @GetMapping("/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("faculties", facultyRepository.findAll());
        return "students/form";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("student", StudentMapper.toDto(student));
        model.addAttribute("faculties", facultyRepository.findAll());
        return "students/form";
    }

    @PostMapping("/save")
    public String saveStudent(
            @Valid @ModelAttribute("student") StudentDTO studentDTO,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("faculties", facultyRepository.findAll());
            return "students/form";
        }

        Student student;

        if (studentDTO.getId() != null) {
            student = studentRepository.findById(studentDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
        } else {
            student = new Student();
        }

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());

        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        student.setFaculty(faculty);

        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}