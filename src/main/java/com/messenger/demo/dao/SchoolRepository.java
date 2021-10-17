package com.messenger.demo.dao;

import com.messenger.demo.entity.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {

    @Override
    Optional<School> findById(Long id);
}
