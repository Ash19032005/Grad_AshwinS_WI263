package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Student;
import com.example.demo.repo.StudentRepo;

@Service
public class StudentService {

    @Autowired
    private StudentRepo repo;

    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    public Optional<Student> getStudent(int regNo) {
        return repo.findById(regNo);
    }

    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    public Student updateStudent(int regNo, Student student) {
        student.setRegNo(regNo);
        return repo.save(student);
    }

    public Student patchStudent(int regNo, Student student) {
        Student existing = repo.findById(regNo).get();

        if (student.getName() != null)
            existing.setName(student.getName());
        if (student.getSchool() != null)
            existing.setSchool(student.getSchool());
        if (student.getStandard() != null)
            existing.setStandard(student.getStandard());
        if (student.getGender() != null)
            existing.setGender(student.getGender());
        if (student.getPercentage() != 0)
            existing.setPercentage(student.getPercentage());

        return repo.save(existing);
    }

    public void deleteStudent(int regNo) {
        repo.deleteById(regNo);
    }

    public List<Student> getBySchool(String school) {
        return repo.findBySchool(school);
    }

    public long countBySchool(String school) {
        return repo.countBySchool(school);
    }

    public long countByStandard(String standard) {
        return repo.countByStandard(standard);
    }

    public List<Student> getResult(boolean pass) {
        return pass ? repo.findPassedStudents() : repo.findFailedStudents();
    }

    public long getStrength(String gender, String standard) {
        return repo.countByGenderAndStandard(gender, standard);
    }
}
