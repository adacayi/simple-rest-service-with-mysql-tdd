package com.sanver.trials.simplerestservicewithmysqltdd.controllers;

import com.sanver.trials.simplerestservicewithmysqltdd.models.Student;
import com.sanver.trials.simplerestservicewithmysqltdd.models.StudentDTO;
import com.sanver.trials.simplerestservicewithmysqltdd.models.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository repository;

    @GetMapping("{id}")
    public StudentDTO getStudent(@PathVariable long id) {
        Student student = repository.findById(id).orElse(null);

        if (student == null)
            return null;

        StudentDTO result = new StudentDTO();
        BeanUtils.copyProperties(student, result);
        return result;
    }

    @GetMapping
    public List<StudentDTO> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Student> studentList = repository.findAll(PageRequest.of(page, size)).getContent();

        if (studentList == null)
            return null;

        List<StudentDTO> result = new ArrayList<>(studentList.size());
        studentList.forEach(s -> {
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(s, studentDTO);
            result.add(studentDTO);
        });

        return result;
    }

    @PostMapping
    public StudentDTO postStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student = repository.save(student);
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @PutMapping("{id}")
    public StudentDTO putStudent(@PathVariable long id, @RequestBody StudentDTO studentDTO) {
        Student student = repository.findById(id).orElse(null);

        if (student == null)
            return null;

        BeanUtils.copyProperties(studentDTO, student);
        student = repository.save(student);
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable long id) {
        repository.deleteById(id);
    }
}
