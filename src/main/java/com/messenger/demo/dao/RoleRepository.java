package com.messenger.demo.dao;

import com.messenger.demo.entity.Role;
import com.messenger.demo.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Override
    Optional<Role> findById(Integer id);

}
