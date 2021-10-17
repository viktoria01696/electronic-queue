package com.messenger.demo.service.impl;

import com.messenger.demo.dao.ChatRepository;
import com.messenger.demo.dto.ChatDto;
import com.messenger.demo.dto.MessageDto;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.entity.Chat;
import com.messenger.demo.entity.Student;
import com.messenger.demo.mapper.ChatMapper;
import com.messenger.demo.mapper.MessageMapper;
import com.messenger.demo.mapper.StudentMapper;
import com.messenger.demo.service.ChatService;
import com.messenger.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final StudentService studentService;
    private final ChatMapper chatMapper;
    private final StudentMapper studentMapper;
    private final MessageMapper messageMapper;

    @Override
    public void createNewChat(Long studentId, ChatDto chatDto) {
        Chat chat = chatMapper.toEntity(chatDto);
        Student student = studentService.findStudentEntityById(studentId);
        if (student != null) {
            chat.setStudentList(new ArrayList<>());
            chat.getStudentList().add(student);
            chatRepository.save(chat);
        }
    }

    @Override
    public void deleteChat(Long chatId) {
        Chat chat = findChatEntityById(chatId);
        if (chat != null) {
            chatRepository.delete(chat);
        }
    }

    @Override
    public List<StudentDto> getChatMembers(Long id) {
        Chat chat = findChatEntityById(id);
        if (chat != null) {
            return chat.getStudentList().stream().map(studentMapper::toDto).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void addChatMember(Long chatId, Long studentId) {
        Chat chat = findChatEntityById(chatId);
        Student student = studentService.findStudentEntityById(studentId);
        if ((chat != null) && (student != null)) {
            if (chat.getStudentList() == null) {
                chat.setStudentList(new ArrayList<>());
            }
            if (student.getChatList() == null) {
                student.setChatList(new ArrayList<>());
            }
            chat.getStudentList().add(student);
            student.getChatList().add(chat);
            chatRepository.save(chat);
        }
    }

    @Override
    public List<MessageDto> getMessages(Long id) {
        Chat chat = findChatEntityById(id);
        if (chat != null) {
            return chat.getMessageList().stream().map(messageMapper::toDto).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Chat findChatEntityById(Long id) {
        return chatRepository.findById(id).orElse(null);
    }

}
