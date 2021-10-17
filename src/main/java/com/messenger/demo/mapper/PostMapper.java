package com.messenger.demo.mapper;

import com.messenger.demo.dao.AttachmentRepository;
import com.messenger.demo.dao.StudentRepository;
import com.messenger.demo.dto.PostDto;
import com.messenger.demo.entity.Attachment;
import com.messenger.demo.entity.Post;
import com.messenger.demo.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Component
public class PostMapper extends AbstractMapper<Post, PostDto> {

    private final StudentRepository studentRepository;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public PostMapper(ModelMapper mapper, StudentRepository studentRepository,
                      AttachmentRepository attachmentRepository) {
        super(mapper, Post.class, PostDto.class);
        this.mapper = mapper;
        this.studentRepository = studentRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Post.class, PostDto.class)
                .addMappings(m -> m.skip(PostDto::setStudentId))
                .addMappings(m -> m.skip(PostDto::setAttachmentId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(PostDto.class, Post.class)
                .addMappings(m -> m.skip(Post::setStudent))
                .addMappings(m -> m.skip(Post::setAttachment))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(Post source, PostDto destination) {
        destination.setStudentId(Objects.isNull(source.getStudent()) ?
                null : source.getStudent().getId());
        destination.setAttachmentId(Objects.isNull(source.getAttachment()) ?
                null : source.getAttachment().getId());
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(PostDto source, Post destination) {
        Optional<Student> student = studentRepository.findById(source.getStudentId());
        Optional<Attachment> attachment = attachmentRepository.findById(source.getAttachmentId());
        destination.setStudent(Objects.isNull(source.getStudentId()) ?
                null : student.orElse(null));
        destination.setAttachment(Objects.isNull(source.getAttachmentId()) ?
                null : attachment.orElse(null));
    }
}
