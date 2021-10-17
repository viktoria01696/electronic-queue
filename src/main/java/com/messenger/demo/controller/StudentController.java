package com.messenger.demo.controller;

import com.messenger.demo.dto.ChatDto;
import com.messenger.demo.dto.PostDto;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.service.ChatService;
import com.messenger.demo.service.PostService;
import com.messenger.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final PostService postService;
    private final ChatService chatService;

    @GetMapping("/all")
    public List<StudentDto> getStudentsList() {
        return studentService.findAllStudents();
    }

    //TODO: расширить метод после добавления фронта
    @GetMapping("/new")
    public StudentDto createNewStudent() {
        return studentService.getNewStudent();
    }

    @PostMapping
    public void submitStudentForm(@Valid @RequestBody StudentDto studentDto) {
        studentService.saveStudent(studentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentProfile(@PathVariable("id") Long id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("/{id}/friends")
    public List<StudentDto> getFriendsList(@PathVariable("id") Long id) {
        return studentService.getFriendsByStudentId(id);
    }

    @PostMapping("/{id}/friends")
    public void addFriend(@PathVariable("id") Long id, @RequestParam("friend_id") Long friendId) {
        studentService.addNewFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friend_id}")
    public void deleteFriend(@PathVariable("id") Long id, @PathVariable("friend_id") Long friendId) {
        studentService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getStudentPosts(@PathVariable("id") Long id) {
        return studentService.getPostsByStudentId(id);
    }

    @PostMapping("/{id}/posts")
    public void addNewPost(@PathVariable("id") Long studentId, @Valid @RequestBody PostDto postDto) {
        postService.createNewPost(studentId, postDto);
    }

    @DeleteMapping("/{id}/posts/{post_id}")
    public void deletePost(@PathVariable("id") Long studentId, @PathVariable("post_id") Long postId) {
        postService.deletePost(studentId, postId);
    }

    @GetMapping("/{id}/chats")
    public List<ChatDto> getStudentChats(@PathVariable("id") Long id) {
        return studentService.getChatsByStudentId(id);
    }

    @PostMapping("/{id}/chats")
    public void addNewChat(@PathVariable("id") Long studentId, @Valid @RequestBody ChatDto chatDto) {
        chatService.createNewChat(studentId, chatDto);
    }

    @DeleteMapping("/chats/{chat_id}")
    public void deleteChat(@PathVariable("chat_id") Long chatId) {
        chatService.deleteChat(chatId);
    }
}
