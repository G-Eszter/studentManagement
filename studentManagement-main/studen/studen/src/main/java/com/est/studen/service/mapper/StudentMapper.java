package com.est.studen.service.mapper;

import com.est.studen.data.entity.Student;
import com.est.studen.service.dto.StudentDTO;

public class StudentMapper {

    public static Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());

        return student;
    }

    public static StudentDTO toDto(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        if (student.getFaculty() != null) {
            dto.setFacultyId(student.getFaculty().getId());
        }
        return dto;
    }
}