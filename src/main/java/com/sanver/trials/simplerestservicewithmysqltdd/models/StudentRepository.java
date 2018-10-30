package com.sanver.trials.simplerestservicewithmysqltdd.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
