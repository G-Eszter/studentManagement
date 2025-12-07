package com.est.studen;

import com.est.studen.data.entity.Faculty;
import com.est.studen.data.entity.Student;
import com.est.studen.data.repository.FacultyRepository;
import com.est.studen.data.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StudenApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudenApplication.class, args);
    }
    @Bean
    public CommandLineRunner loadData(FacultyRepository facultyRepo, StudentRepository studentRepo) {
        return args -> {
            // Faculties
            Faculty engineering = new Faculty();
            engineering.setFacultyName("Engineering");
            engineering.setLocation("Building A");
            engineering.setDean("Dr. Kovács");

            Faculty science = new Faculty();
            science.setFacultyName("Science");
            science.setLocation("Building B");
            science.setDean("Dr. Nagy");

            facultyRepo.saveAll(List.of(engineering, science));

            // Students
            Student s1 = new Student();
            s1.setFirstName("Réka");
            s1.setLastName("Nagy");
            s1.setEmail("reka.nagy@example.com");
            s1.setFaculty(engineering);

            Student s2 = new Student();
            s2.setFirstName("Gábor");
            s2.setLastName("Kiss");
            s2.setEmail("gabor.kiss@example.com");
            s2.setFaculty(science);

            Student s3 = new Student();
            s3.setFirstName("Anna");
            s3.setLastName("Tóth");
            s3.setEmail("anna.toth@example.com");
            s3.setFaculty(engineering);

            studentRepo.saveAll(List.of(s1, s2, s3));

            System.out.println("Data uploading successful");
        };
    }
}
