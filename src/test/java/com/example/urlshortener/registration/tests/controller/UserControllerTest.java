package com.example.urlshortener.registration.tests.controller;

import com.example.urlshortener.registration.DTO.UserDTO;
import com.example.urlshortener.registration.DTO.UserResponseDTO;
import com.example.urlshortener.registration.controller.UserController;
import com.example.urlshortener.registration.repository.UserRepository;
import com.example.urlshortener.registration.service.UserService;
import com.example.urlshortener.registration.util.PassGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private UserController userController;

    @Mock
    private UserRepository userRepository;


    @Test
    public void controllerTestRegisterUser_Success() {

        UserDTO userDTO = new UserDTO();
        userDTO.setAccountID("zoki123");

        UserService userService = Mockito.mock(UserService.class);
        UserResponseDTO expectedResponse = new UserResponseDTO();
        expectedResponse.setSuccess(true);
        expectedResponse.setPassword(PassGenerator.generateRandomPassword(10));
        Mockito.when(userService.registerUser(userDTO)).thenReturn(expectedResponse);

        UserController userController = new UserController(userService);

        UserResponseDTO response = userController.registerUser(userDTO);

        assertTrue(response.isSuccess());
        assertNotNull(response.getPassword());
        assertNull(response.getDescription());
    }

    @Test
    public void controllerTestRegisterUserAccountExists() {

        UserDTO userDTO = new UserDTO();
        userDTO.setAccountID("john_doe");

        UserService userService = Mockito.mock(UserService.class);
        UserResponseDTO expectedResponse = new UserResponseDTO();
        expectedResponse.setSuccess(false);
        expectedResponse.setDescription("Account ID already exists!");
        Mockito.when(userService.registerUser(userDTO)).thenReturn(expectedResponse);

        UserController userController = new UserController(userService);

        UserResponseDTO response = userController.registerUser(userDTO);

        assertFalse(response.isSuccess());
        assertNull(response.getPassword());
        assertEquals("Account ID already exists!", response.getDescription());
    }

}