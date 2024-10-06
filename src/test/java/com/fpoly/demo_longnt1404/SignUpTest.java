package com.fpoly.demo_longnt1404;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fpoly.demo_longnt1404.controller.SignUpController;
import com.fpoly.demo_longnt1404.dto.RoleDTO;
import com.fpoly.demo_longnt1404.dto.UserDTO;
import com.fpoly.demo_longnt1404.exception.GlobalExceptionHandler;
import com.fpoly.demo_longnt1404.mapper.UserMapper;
import com.fpoly.demo_longnt1404.service.OtpService;
import com.fpoly.demo_longnt1404.service.UserService;
import com.fpoly.demo_longnt1404.service.impl.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
public class SignUpTest {
    @Mock
    private UserService userService;

    @Mock
    private OtpService otpService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SignUpController signUpController;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateUser_Success() throws Exception {
        // Create UserDTO object
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("Abcd1234");
        userDTO.setEmail("longnt14042004@gmail.com");
        userDTO.setFullname("Nguyen Trong Long");
        userDTO.setRoles(List.of(new RoleDTO(2, "USER")));

        // Initialize MockMvc object
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(signUpController).build();

        // Send POST request to /signup
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated()) // Check the status code is 201
                .andExpect(jsonPath("$").value("OTP code has been sent to your email")); // Check the response content
    }

    @Test
    void testCreateUserFail_usernameIsBlank() throws Exception {
        // Create UserDTO object
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("");
        userDTO.setPassword("Abcd1234");
        userDTO.setEmail("longnt14042004@gmail.com");
        userDTO.setFullname("Nguyen Trong Long");
        userDTO.setRoles(List.of(new RoleDTO(2, "USER")));

        // Initialize MockMvc object
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(signUpController)
                .setControllerAdvice(new GlobalExceptionHandler())  // Register GlobalExceptionHandler
                .build();

        // Send POST request to /signup
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())  // Check the status code is 400
                .andExpect(jsonPath("$.username").value("Username is required"));  // Check validation message
    }


    @Test
    void testCreateUserFail_passwordIsBlank() throws Exception {
        // Create UserDTO object
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("");
        userDTO.setEmail("longnt14042004@gmail.com");
        userDTO.setFullname("Nguyen Trong Long");
        userDTO.setRoles(List.of(new RoleDTO(2, "USER")));

        // Initialize MockMvc object
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(signUpController)
                .setControllerAdvice(new GlobalExceptionHandler())  // Register GlobalExceptionHandler
                .build();

        // Send POST request to /signup
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())  // Check the status code is 400
                .andExpect(jsonPath("$.password").value("Password is required"));  // Check validation message
    }

    @Test
    void testCreateUserFail_passwordIsInvalid() throws Exception {
        // Create UserDTO object
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("1234");
        userDTO.setEmail("longnt14042004@gmail.com");
        userDTO.setFullname("Nguyen Trong Long");
        userDTO.setRoles(List.of(new RoleDTO(2, "USER")));

        // Initialize MockMvc object
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(signUpController)
                .setControllerAdvice(new GlobalExceptionHandler())  // Register GlobalExceptionHandler
                .build();

        // Send POST request to /signup
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())  // Check the status code is 400
                .andExpect(jsonPath("$.password").value("Password must contain at least 8 characters, including uppercase, lowercase letters and numbers"));  // Check validation message
    }

    @Test
    void testCreateUserFail_emailIsBlank() throws Exception {
        // Create UserDTO object
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("Abcd1234");
        userDTO.setEmail("");
        userDTO.setFullname("Nguyen Trong Long");
        userDTO.setRoles(List.of(new RoleDTO(2, "USER")));

        // Initialize MockMvc object
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(signUpController)
                .setControllerAdvice(new GlobalExceptionHandler())  // Register GlobalExceptionHandler
                .build();

        // Send POST request to /signup
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())  // Check the status code is 400
                .andExpect(jsonPath("$.email").value("Email is required"));  // Check validation message
    }

    @Test
    void testCreateUserFail_emailIsInvalid() throws Exception {
        // Create UserDTO object
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("Abcd1234");
        userDTO.setEmail("longnt14042004");
        userDTO.setFullname("Nguyen Trong Long");
        userDTO.setRoles(List.of(new RoleDTO(2, "USER")));

        // Initialize MockMvc object
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(signUpController)
                .setControllerAdvice(new GlobalExceptionHandler())  // Register GlobalExceptionHandler
                .build();

        // Send POST request to /signup
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())  // Check the status code is 400
                .andExpect(jsonPath("$.email").value("Email format is incorrect"));  // Check validation message
    }
}
