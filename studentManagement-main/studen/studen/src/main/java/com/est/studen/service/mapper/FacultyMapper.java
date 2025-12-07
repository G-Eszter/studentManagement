package com.est.studen.service.mapper;

import com.est.studen.service.dto.FacultyDTO;
import com.est.studen.service.dto.StudentDTO;
import com.est.studen.data.entity.Faculty;
import com.est.studen.data.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public class FacultyMapper {

    // Entity → DTO
    public static FacultyDTO toDTO(Faculty faculty) {
        if (faculty == null) return null;

        FacultyDTO dto = new FacultyDTO();
        dto.setId(faculty.getId());
        dto.setFacultyName(faculty.getFacultyName());
        dto.setLocation(faculty.getLocation());
        dto.setDean(faculty.getDean());

        if (faculty.getStudents() != null) {
            List<StudentDTO> studentDTOs = faculty.getStudents()
                    .stream()
                    .map(student -> {
                        StudentDTO sDto = new StudentDTO();
                        sDto.setId(student.getId());
                        sDto.setFirstName(student.getFirstName());
                        sDto.setLastName(student.getLastName());
                        sDto.setEmail(student.getEmail());

                        return sDto;
                    })
                    .collect(Collectors.toList());
            dto.setStudents(studentDTOs);
        }

        return dto;
    }

    // DTO → Entity
    public static Faculty toEntity(FacultyDTO dto) {
        if (dto == null) return null;

        Faculty faculty = new Faculty();
        faculty.setId(dto.getId());
        faculty.setFacultyName(dto.getFacultyName());
        faculty.setLocation(dto.getLocation());
        faculty.setDean(dto.getDean());

        if (dto.getStudents() != null) {
            List<Student> students = dto.getStudents()
                    .stream()
                    .map(sDto -> {
                        Student s = new Student();
                        s.setId(sDto.getId());
                        s.setFirstName(sDto.getFirstName());
                        s.setLastName(sDto.getLastName());
                        s.setEmail(sDto.getEmail());
                        s.setFaculty(faculty);
                        return s;
                    })
                    .collect(Collectors.toList());
            faculty.setStudents(students);
        }

        return faculty;
    }
}
