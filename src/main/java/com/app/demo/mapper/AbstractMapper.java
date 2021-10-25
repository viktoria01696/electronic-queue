package com.app.demo.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractMapper<A, B> {

    private final Class<A> entityClass;
    private final Class<B> dtoClass;
    protected ModelMapper mapper;

    @Autowired
    AbstractMapper(ModelMapper mapper, Class<A> entityClass, Class<B> dtoClass) {
        this.mapper = mapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public A toEntity(B dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    public B toDto(A entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }

    public Converter<A, B> toDtoConverter() {
        return context -> {
            A source = context.getSource();
            B destination = context.getDestination();
            mapSpecificFieldsInDtoConverter(source, destination);
            return context.getDestination();
        };
    }

    public Converter<B, A> toEntityConverter() {
        return context -> {
            B source = context.getSource();
            A destination = context.getDestination();
            mapSpecificFieldsInEntityConverter(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFieldsInDtoConverter(A source, B destination) {
    }

    void mapSpecificFieldsInEntityConverter(B source, A destination) {
    }

}
