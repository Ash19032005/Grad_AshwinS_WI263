package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    List<Student> findBySchool(String school);

    long countBySchool(String school);

    long countByStandard(String standard);

    @Query("SELECT s FROM Student s WHERE s.percentage >= 40 ORDER BY s.percentage DESC")
    List<Student> findPassedStudents();

    @Query("SELECT s FROM Student s WHERE s.percentage < 40 ORDER BY s.percentage DESC")
    List<Student> findFailedStudents();

    long countByGenderAndStandard(String gender, String standard);
}


