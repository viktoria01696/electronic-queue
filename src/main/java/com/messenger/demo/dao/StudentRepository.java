package com.messenger.demo.dao;

import com.messenger.demo.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    @Override
    Optional<Student> findById(Long id);

    Student findByLogin(String login);
}
