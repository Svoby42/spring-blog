package com.example.springblog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(255)")
    private UUID id;

    @Column(name = "title", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;

    @Column(name = "content", length = 10000)             //is nullable
    private String content;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime create_time;

    @Column(name = "edit_time")
    private LocalDateTime edit_time;

    @Column(name = "author_name")
    private String author_name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

}
