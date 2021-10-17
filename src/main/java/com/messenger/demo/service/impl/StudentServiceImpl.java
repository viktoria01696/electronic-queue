package com.messenger.demo.service.impl;

import com.messenger.demo.dao.StudentRepository;
import com.messenger.demo.dto.ChatDto;
import com.messenger.demo.dto.PostDto;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.entity.Student;
import com.messenger.demo.mapper.ChatMapper;
import com.messenger.demo.mapper.PostMapper;
import com.messenger.demo.mapper.StudentMapper;
import com.messenger.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PostMapper postMapper;
    private final ChatMapper chatMapper;

    @Override
    public List<StudentDto> findAllStudents() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), true)
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getNewStudent() {
        return new StudentDto();
    }

    @Override
    public void saveStudent(StudentDto studentDto) {
        studentRepository.save(studentMapper.toEntity(studentDto));
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = findStudentEntityById(id);
        if (student != null) {
            student.getSchool().getStudentList().remove(student);
            studentRepository.deleteById(id);
        }
    }

    @Override
    public StudentDto findStudentById(Long id) {
        return studentMapper.toDto(findStudentEntityById(id));
    }

    @Override
    public List<StudentDto> getFriendsByStudentId(Long id) {
        Student student = findStudentEntityById(id);
        if (student != null) {
            return student.getFriendList().stream().map(studentMapper::toDto).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void addNewFriend(Long id, Long friendId) {
        Student currentStudent = findStudentEntityById(id);
        Student newFriend = findStudentEntityById(friendId);
        if ((currentStudent != null) && (newFriend != null)) {
            if (currentStudent.getFriendList() == null) {
                currentStudent.setFriendList(new ArrayList<>());
            }
            if (newFriend.getFriendList() == null) {
                newFriend.setFriendList(new ArrayList<>());
            }
            currentStudent.getFriendList().add(newFriend);
            newFriend.getFriendList().add(currentStudent);
            studentRepository.save(currentStudent);
        }
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        Student currentStudent = findStudentEntityById(id);
        Student oldFriend = findStudentEntityById(friendId);
        if ((currentStudent != null) && (oldFriend != null)) {
            if (currentStudent.getFriendList().contains(oldFriend)
                    && oldFriend.getFriendList().contains(currentStudent)) {
                currentStudent.getFriendList().remove(oldFriend);
                oldFriend.getFriendList().remove(currentStudent);
                studentRepository.save(currentStudent);
            }
        }
    }

    @Override
    public List<PostDto> getPostsByStudentId(Long id) {
        Student student = findStudentEntityById(id);
        if (student != null) {
            return student.getPostList().stream().map(postMapper::toDto).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ChatDto> getChatsByStudentId(Long id) {
        Student student = findStudentEntityById(id);
        if (student != null) {
            return student.getChatList().stream().map(chatMapper::toDto).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Student findStudentEntityById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

}
