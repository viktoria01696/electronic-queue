package com.messenger.demo.mapper;

import com.messenger.demo.dao.AttachmentRepository;
import com.messenger.demo.dao.ChatRepository;
import com.messenger.demo.dao.MessageRepository;
import com.messenger.demo.dao.StudentRepository;
import com.messenger.demo.dto.MessageDto;
import com.messenger.demo.entity.Attachment;
import com.messenger.demo.entity.Chat;
import com.messenger.demo.entity.Message;
import com.messenger.demo.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Component
public class MessageMapper extends AbstractMapper<Message, MessageDto> {

    private final MessageRepository messageRepository;
    private final StudentRepository studentRepository;
    private final ChatRepository chatRepository;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public MessageMapper(ModelMapper mapper, MessageRepository messageRepository, StudentRepository studentRepository,
                         ChatRepository chatRepository, AttachmentRepository attachmentRepository) {
        super(mapper, Message.class, MessageDto.class);
        this.mapper = mapper;
        this.messageRepository = messageRepository;
        this.studentRepository = studentRepository;
        this.chatRepository = chatRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Message.class, MessageDto.class)
                .addMappings(m -> m.skip(MessageDto::setStudentId))
                .addMappings(m -> m.skip(MessageDto::setAttachmentId))
                .addMappings(m -> m.skip(MessageDto::setChatId))
                .addMappings(m -> m.skip(MessageDto::setParentMessageId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(MessageDto.class, Message.class)
                .addMappings(m -> m.skip(Message::setStudent))
                .addMappings(m -> m.skip(Message::setAttachment))
                .addMappings(m -> m.skip(Message::setChat))
                .addMappings(m -> m.skip(Message::setParentMessage))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(Message source, MessageDto destination) {
        destination.setStudentId(Objects.isNull(source.getStudent()) ?
                null : source.getStudent().getId());
        destination.setAttachmentId(Objects.isNull(source.getAttachment()) ?
                null : source.getAttachment().getId());
        destination.setChatId(Objects.isNull(source.getChat()) ?
                null : source.getChat().getId());
        destination.setParentMessageId(Objects.isNull(source.getParentMessage()) ?
                null : source.getParentMessage().getId());
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(MessageDto source, Message destination) {
        Optional<Student> student = studentRepository.findById(source.getStudentId());
        Optional<Attachment> attachment = attachmentRepository.findById(source.getAttachmentId());
        Optional<Chat> chat = chatRepository.findById(source.getChatId());
        Optional<Message> message = messageRepository.findById(source.getParentMessageId());
        destination.setStudent(Objects.isNull(source.getStudentId()) ?
                null : student.orElse(null));
        destination.setAttachment(Objects.isNull(source.getAttachmentId()) ?
                null : attachment.orElse(null));
        destination.setChat(Objects.isNull(source.getChatId()) ?
                null : chat.orElse(null));
        destination.setParentMessage(Objects.isNull(source.getParentMessageId()) ?
                null : message.orElse(null));
    }
}
