package com.messenger.demo.controller;

import com.messenger.demo.dto.MessageDto;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{id}/members")
    public List<StudentDto> getChatMembers(@PathVariable("id") Long id) {
        return chatService.getChatMembers(id);
    }

    @GetMapping("/{id}/messages")
    public List<MessageDto> getMessages(@PathVariable("id") Long id) {
        return chatService.getMessages(id);
    }

    @PostMapping("/{id}/add_member")
    public void addChatMember(@PathVariable("id") Long id, @RequestParam("student_id") Long studentId) {
        chatService.addChatMember(id, studentId);
    }


}
