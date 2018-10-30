package com.sanver.trials.simplerestservicewithmysqltdd.controllers;

import com.sanver.trials.simplerestservicewithmysqltdd.models.Student;
import com.sanver.trials.simplerestservicewithmysqltdd.models.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository repository;

    @GetMapping("{id}")
    public Student getStudent(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Student> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @PostMapping
    public Student postStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    @PutMapping("{id}")
    public Student putStudent(@PathVariable long id, @RequestBody Student student) {
        if (student.getId() != id)
            return null;

        if (repository.findById(id).isPresent())
            return repository.save(student);

        return null;
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable long id) {
        repository.deleteById(id);
    }
}
