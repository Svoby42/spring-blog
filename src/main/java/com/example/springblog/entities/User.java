package com.example.springblog.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private UUID id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime create_time;

    @Column(name = "last_login")
    private LocalDateTime last_login;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

//    @Column(name = "enabled", nullable = false)
//    private boolean canPost;

    @Transient
    private String token;

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Article> user_articles;

}
