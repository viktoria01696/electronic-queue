package com.messenger.demo.mapper;

import com.messenger.demo.dao.*;
import com.messenger.demo.dto.StudentDto;
import com.messenger.demo.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StudentMapper extends AbstractMapper<Student, StudentDto> {

    private final MessageRepository messageRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public StudentMapper(ModelMapper mapper, MessageRepository messageRepository,
                         StudentRepository studentRepository, SchoolRepository schoolRepository,
                         PostRepository postRepository, ChatRepository chatRepository) {
        super(mapper, Student.class, StudentDto.class);
        this.mapper = mapper;
        this.messageRepository = messageRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.postRepository = postRepository;
        this.chatRepository = chatRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Student.class, StudentDto.class)
                .addMappings(m -> m.skip(StudentDto::setSchoolId))
                .addMappings(m -> m.skip(StudentDto::setPostIdList))
                .addMappings(m -> m.skip(StudentDto::setMessageIdList))
                .addMappings(m -> m.skip(StudentDto::setChatIdList))
                .addMappings(m -> m.skip(StudentDto::setFriendIdList))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(StudentDto.class, Student.class)
                .addMappings(m -> m.skip(Student::setSchool))
                .addMappings(m -> m.skip(Student::setPostList))
                .addMappings(m -> m.skip(Student::setMessageList))
                .addMappings(m -> m.skip(Student::setChatList))
                .addMappings(m -> m.skip(Student::setFriendList))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(Student source, StudentDto destination) {
        destination.setSchoolId(Objects.isNull(source.getSchool()) ?
                null : source.getSchool().getId());
        destination.setPostIdList(Objects.isNull(source.getPostList()) ?
                null : source.getPostList().stream().map(Post::getId).collect(Collectors.toList()));
        destination.setMessageIdList(Objects.isNull(source.getMessageList()) ?
                null : source.getMessageList().stream().map(Message::getId).collect(Collectors.toList()));
        destination.setChatIdList(Objects.isNull(source.getChatList()) ?
                null : source.getChatList().stream().map(Chat::getId).collect(Collectors.toList()));
        destination.setFriendIdList(Objects.isNull(source.getFriendList()) ?
                null : source.getFriendList().stream().map(Student::getId).collect(Collectors.toList()));
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(StudentDto source, Student destination) {
        Optional<School> school = schoolRepository.findById(source.getSchoolId());
        destination.setSchool(Objects.isNull(source.getSchoolId()) ?
                null : school.orElse(null));
        destination.setPostList(Objects.isNull(source.getPostIdList()) ?
                null : source.getPostIdList().stream().map(postRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
        destination.setMessageList(Objects.isNull(source.getMessageIdList()) ?
                null : source.getMessageIdList().stream().map(messageRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
        destination.setChatList(Objects.isNull(source.getChatIdList()) ?
                null : source.getChatIdList().stream().map(chatRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
        destination.setFriendList(Objects.isNull(source.getFriendIdList()) ?
                null : source.getFriendIdList().stream().map(studentRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
    }
}
