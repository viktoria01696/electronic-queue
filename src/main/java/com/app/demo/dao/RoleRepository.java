package com.app.demo.dao;

import com.app.demo.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Override
    Optional<Role> findById(Long id);

    Role findRoleByName(String name);

}
