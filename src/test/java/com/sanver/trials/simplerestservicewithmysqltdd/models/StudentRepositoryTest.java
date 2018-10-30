package com.sanver.trials.simplerestservicewithmysqltdd.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;

    @Test
    public void should_return_count() {
        long count = repository.count();
        assertThat(count, greaterThanOrEqualTo(1L));
    }

    @Test
    public void should_calculate_age() {
        int age = 20;
        Student student = new Student("Ahmet", LocalDate.now().minusYears(age));
        Student saved = repository.save(student);
        saved = repository.findById(saved.getId()).orElse(null);
        assertNotNull(saved);
        assertEquals(age, saved.getAge());

        student = new Student("Mehmet", LocalDate.now().minusYears(age).plusDays(1));
        saved = repository.save(student);
        saved = repository.findById(saved.getId()).orElse(null);
        assertNotNull(saved);
        assertEquals(age - 1, saved.getAge());

        student = new Student("Sadi", LocalDate.now().minusYears(1).plusDays(1));
        saved = repository.save(student);
        saved = repository.findById(saved.getId()).orElse(null);
        assertNotNull(saved);
        assertEquals(0, saved.getAge());
    }
}
