package com.messenger.demo.dao;

import com.messenger.demo.entity.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    @Override
    Optional<Attachment> findById(Long id);
}
