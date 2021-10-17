package com.messenger.demo.dao;

import com.messenger.demo.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    @Override
    Optional<Post> findById(Long id);
}
