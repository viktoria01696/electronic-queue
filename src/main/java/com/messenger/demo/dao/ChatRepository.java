package com.messenger.demo.dao;

import com.messenger.demo.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

    @Override
    Optional<Chat> findById(Long id);
}
