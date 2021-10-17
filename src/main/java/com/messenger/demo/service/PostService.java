package com.messenger.demo.service;

import com.messenger.demo.dto.PostDto;
import com.messenger.demo.entity.Post;

public interface PostService {

    void createNewPost(Long studentId, PostDto postDto);

    void deletePost(Long studentId, Long postId);

    Post findPostEntityById(Long id);

}
