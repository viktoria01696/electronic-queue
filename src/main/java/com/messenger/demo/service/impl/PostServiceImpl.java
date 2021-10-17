package com.messenger.demo.service.impl;

import com.messenger.demo.dao.PostRepository;
import com.messenger.demo.dto.PostDto;
import com.messenger.demo.entity.Post;
import com.messenger.demo.entity.Student;
import com.messenger.demo.mapper.PostMapper;
import com.messenger.demo.service.PostService;
import com.messenger.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final StudentService studentService;
    private final PostMapper postMapper;

    @Override
    public void createNewPost(Long studentId, PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        Student student = studentService.findStudentEntityById(studentId);
        if (student != null) {
            post.setStudent(student);
            postRepository.save(post);
        }
    }

    @Override
    public void deletePost(Long studentId, Long postId) {
        Post post = findPostEntityById(postId);
        Student student = studentService.findStudentEntityById(studentId);
        if ((post != null) && (student != null)) {
            student.getPostList().remove(post);
            postRepository.delete(post);
        }
    }

    @Override
    public Post findPostEntityById(Long id) {
        return postRepository.findById(id).orElse(null);
    }


}
