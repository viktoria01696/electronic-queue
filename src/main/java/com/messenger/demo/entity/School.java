package com.messenger.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Size(max = 255)
    @Column(name = "school_name")
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "school")
    private List<Student> studentList;

    public School(String address) {
        this.address = address;
    }

    @PreRemove
    private void preRemove() {
        studentList.forEach(student -> student.setSchool(null));
    }
}
