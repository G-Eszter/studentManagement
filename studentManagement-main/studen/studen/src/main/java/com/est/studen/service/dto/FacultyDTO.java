package com.est.studen.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FacultyDTO {
    private Long id;

    @NotBlank(message = "Faculty name is required")
    private String facultyName;
    private String location;
    private String dean;

    List<StudentDTO> students;

}
