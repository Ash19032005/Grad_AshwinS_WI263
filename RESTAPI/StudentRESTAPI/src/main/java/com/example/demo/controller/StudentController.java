package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    // GET /students
    @GetMapping
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    // GET /students/{regNo}
    @GetMapping("/{regNo}")
    public Student getStudent(@PathVariable int regNo) {
        return service.getStudent(regNo).get();
    }

    // POST /students
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    // PUT /students/{regNo}
    @PutMapping("/{regNo}")
    public Student update(@PathVariable int regNo, @RequestBody Student student) {
        return service.updateStudent(regNo, student);
    }

    // PATCH /students/{regNo}
    @PatchMapping("/{regNo}")
    public Student patch(@PathVariable int regNo, @RequestBody Student student) {
        return service.patchStudent(regNo, student);
    }

    // DELETE /students/{regNo}
    @DeleteMapping("/{regNo}")
    public void delete(@PathVariable int regNo) {
        service.deleteStudent(regNo);
    }

    // GET /students/school?name=KV
    @GetMapping("/school")
    public List<Student> getBySchool(@RequestParam String name) {
        return service.getBySchool(name);
    }

    // GET /students/school/count?name=DPS
    @GetMapping("/school/count")
    public long countSchool(@RequestParam String name) {
        return service.countBySchool(name);
    }

    // GET /students/school/standard/count?class=5
    @GetMapping("/school/standard/count")
    public long countStandard(@RequestParam("class") String standard) {
        return service.countByStandard(standard);
    }

    // GET /students/result?pass=true/false
    @GetMapping("/result")
    public List<Student> result(@RequestParam boolean pass) {
        return service.getResult(pass);
    }

    // GET /students/strength?gender=MALE&standard=5
    @GetMapping("/strength")
    public long strength(@RequestParam String gender,
                         @RequestParam String standard) {
        return service.getStrength(gender, standard);
    }
}
