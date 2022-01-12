package com.example.springblog.controllers;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.ArticleRepository;
import com.example.springblog.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;


    @Test
    public void findAuthorOfArticle_Success() throws Exception{
        User user = new  User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setName("Petr");
        user.setUsername("petan410");
        user.setPassword("konak410");
        user.setCreate_time(LocalDateTime.now());
        user.setRole(Role.USER);

        Article article = new Article();
        article.setId(String.valueOf(UUID.randomUUID()));
        article.setTitle("Pokus");
        article.setContent("pokus");
        article.setSlug("pokus1");
        article.setUser(user);

        when(articleRepository.findBySlug(article.getSlug())).thenReturn(Optional.of(article));

        assertEquals("pokus1", articleRepository.findBySlug("pokus1").get().getSlug());
        assertEquals(user, articleRepository.findBySlug("pokus1").get().getUser());

    }

}
