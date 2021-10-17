package com.messenger.demo.service;

import com.messenger.demo.dto.ChatDto;
import com.messenger.demo.dto.PostDto;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.entity.Student;

import java.util.List;

public interface StudentService {

    List<StudentDto> findAllStudents();

    StudentDto getNewStudent();

    void saveStudent(StudentDto studentDto);

    void deleteStudentById(Long id);

    StudentDto findStudentById(Long id);

    List<StudentDto> getFriendsByStudentId(Long id);

    List<PostDto> getPostsByStudentId(Long id);

    List<ChatDto> getChatsByStudentId(Long id);

    void addNewFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);

    Student findStudentEntityById(Long id);

}
