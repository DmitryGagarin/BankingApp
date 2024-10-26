package com.bank.user_service;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void registerUser_ShouldReturnSuccessMessage() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";

        when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(new User(firstName, lastName, email, password));

        mockMvc.perform(post("/register/{firstName}/{lastName}/{email}/{password}", firstName, lastName, email, password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully."));
    }
}
