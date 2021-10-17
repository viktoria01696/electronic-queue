package com.messenger.demo.mapper;

import com.messenger.demo.dao.MessageRepository;
import com.messenger.demo.dao.PostRepository;
import com.messenger.demo.dto.AttachmentDto;
import com.messenger.demo.entity.Attachment;
import com.messenger.demo.entity.Message;
import com.messenger.demo.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Component
public class AttachmentMapper extends AbstractMapper<Attachment, AttachmentDto> {

    private final MessageRepository messageRepository;
    private final PostRepository postRepository;


    @Autowired
    public AttachmentMapper(ModelMapper mapper, MessageRepository messageRepository,
                            PostRepository postRepository) {
        super(mapper, Attachment.class, AttachmentDto.class);
        this.mapper = mapper;
        this.messageRepository = messageRepository;
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Attachment.class, AttachmentDto.class)
                .addMappings(m -> m.skip(AttachmentDto::setMessageId))
                .addMappings(m -> m.skip(AttachmentDto::setPostId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(AttachmentDto.class, Attachment.class)
                .addMappings(m -> m.skip(Attachment::setMessage))
                .addMappings(m -> m.skip(Attachment::setPost))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(Attachment source, AttachmentDto destination) {
        destination.setMessageId(Objects.isNull(source.getMessage()) ?
                null : source.getMessage().getId());
        destination.setPostId(Objects.isNull(source.getPost()) ?
                null : source.getPost().getId());
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(AttachmentDto source, Attachment destination) {
        Optional<Message> message = messageRepository.findById(source.getMessageId());
        Optional<Post> post = postRepository.findById(source.getPostId());
        destination.setMessage(Objects.isNull(source.getMessageId()) ?
                null : message.orElse(null));
        destination.setPost(Objects.isNull(source.getPostId()) ?
                null : post.orElse(null));
    }
}
