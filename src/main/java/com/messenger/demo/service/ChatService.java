package com.messenger.demo.service;

import com.messenger.demo.dto.ChatDto;
import com.messenger.demo.dto.MessageDto;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.entity.Chat;

import java.util.List;

public interface ChatService {

    void createNewChat(Long studentId, ChatDto chatDto);

    void deleteChat(Long chatId);

    Chat findChatEntityById(Long id);

    List<StudentDto> getChatMembers(Long id);

    List<MessageDto> getMessages(Long id);

    void addChatMember(Long chatId, Long studentId);

}
