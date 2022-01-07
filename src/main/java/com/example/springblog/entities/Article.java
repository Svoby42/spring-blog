package com.example.springblog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "articles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "topic_content", length = 10000)             //is nullable
    private String topicContent;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "author_name", nullable = true)
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

}
