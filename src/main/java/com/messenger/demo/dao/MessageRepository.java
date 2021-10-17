package com.messenger.demo.dao;

import com.messenger.demo.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Override
    Optional<Message> findById(Long id);
}
