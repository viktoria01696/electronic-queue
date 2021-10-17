package com.messenger.demo.mapper;

import com.messenger.demo.dao.StudentRepository;
import com.messenger.demo.dto.SchoolDto;
import com.messenger.demo.entity.School;
import com.messenger.demo.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SchoolMapper extends AbstractMapper<School, SchoolDto> {

    private final StudentRepository studentRepository;

    @Autowired
    public SchoolMapper(ModelMapper mapper, StudentRepository studentRepository) {
        super(mapper, School.class, SchoolDto.class);
        this.mapper = mapper;
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(School.class, SchoolDto.class)
                .addMappings(m -> m.skip(SchoolDto::setStudentIdList))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(SchoolDto.class, School.class)
                .addMappings(m -> m.skip(School::setStudentList))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(School source, SchoolDto destination) {
        destination.setStudentIdList(Objects.isNull(source.getStudentList()) ?
                null : source.getStudentList().stream().map(Student::getId).collect(Collectors.toList()));
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(SchoolDto source, School destination) {
        destination.setStudentList(Objects.isNull(source.getStudentIdList()) ?
                null : source.getStudentIdList().stream().map(studentRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
    }
}
