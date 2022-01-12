package com.example.springblog.controllers;

import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.UserRepository;
import com.example.springblog.services.IAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IAuthenticationService authenticationService;

    @Test
    public void signUp_Success() throws Exception{
        User user = new  User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setName("Petr");
        user.setUsername("petan410");
        user.setPassword("konak410");
        user.setCreate_time(LocalDateTime.now());
        user.setRole(Role.USER);

        when(userRepository.save(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/authentication/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Petr")));

    }

    @Test
    public void signInAndHasToken_Success() throws Exception{
        User user = new  User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setName("Petr");
        user.setUsername("petan410");
        user.setPassword("konak410");
        user.setCreate_time(LocalDateTime.now());
        user.setRole(Role.USER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/authentication/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Petr")))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn();
    }
}