package com.hseapple.dao;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "course", schema = "public")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    //getters and setters
}
