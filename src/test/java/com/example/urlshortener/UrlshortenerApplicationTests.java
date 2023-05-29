package com.example.urlshortener;

import com.example.urlshortener.registration.controller.UserController;
import com.example.urlshortener.registration.util.PassGenerator;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.urlshortener.registration.DTO.UserDTO;
import com.example.urlshortener.registration.DTO.UserResponseDTO;
import com.example.urlshortener.registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
class UrlshortenerApplicationTests {

    //1.
    //registration_test
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountID("user3");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setDescription("User registered successfully");
        responseDTO.setPassword(PassGenerator.generateRandomPassword(10));

        when(userService.registerUser(any(UserDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/administration/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountID\":\"user3\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.description").value("User registered successfully"))
                .andExpect(jsonPath("$.password").value(responseDTO.getPassword()));

        verify(userService, times(1)).registerUser(any(UserDTO.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountID("existingUser");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setDescription("Account ID already exists!");

        when(userService.registerUser(any(UserDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/administration/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountID\":\"existingUser\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.description").value("Account ID already exists!"))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(userService, times(1)).registerUser(any(UserDTO.class));
        verifyNoMoreInteractions(userService);
    }

    //1.
}
