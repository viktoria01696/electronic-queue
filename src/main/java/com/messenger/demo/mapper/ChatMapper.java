package com.messenger.demo.mapper;

import com.messenger.demo.dao.MessageRepository;
import com.messenger.demo.dao.StudentRepository;
import com.messenger.demo.dto.ChatDto;
import com.messenger.demo.entity.Chat;
import com.messenger.demo.entity.Message;
import com.messenger.demo.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ChatMapper extends AbstractMapper<Chat, ChatDto> {

    private final MessageRepository messageRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ChatMapper(ModelMapper mapper, MessageRepository messageRepository,
                      StudentRepository studentRepository) {
        super(mapper, Chat.class, ChatDto.class);
        this.mapper = mapper;
        this.messageRepository = messageRepository;
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Chat.class, ChatDto.class)
                .addMappings(m -> m.skip(ChatDto::setStudentIdList))
                .addMappings(m -> m.skip(ChatDto::setMessageIdList))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(ChatDto.class, Chat.class)
                .addMappings(m -> m.skip(Chat::setStudentList))
                .addMappings(m -> m.skip(Chat::setMessageList))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(Chat source, ChatDto destination) {
        destination.setStudentIdList(Objects.isNull(source.getStudentList()) ?
                null : source.getStudentList().stream().map(Student::getId).collect(Collectors.toList()));
        destination.setStudentIdList(Objects.isNull(source.getMessageList()) ?
                null : source.getMessageList().stream().map(Message::getId).collect(Collectors.toList()));
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(ChatDto source, Chat destination) {
        destination.setStudentList(Objects.isNull(source.getStudentIdList()) ?
                null : source.getStudentIdList().stream().map(studentRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
        destination.setMessageList(Objects.isNull(source.getMessageIdList()) ?
                null : source.getMessageIdList().stream().map(messageRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
    }
}
