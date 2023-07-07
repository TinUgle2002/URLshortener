package com.example.urlshortener.registration.service;

import com.example.urlshortener.registration.DTO.UserDTO;
import com.example.urlshortener.registration.DTO.UserResponseDTO;
import com.example.urlshortener.registration.entity.User;
import com.example.urlshortener.registration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUserTestSuccess() {

        UserDTO user1 = new UserDTO();
        user1.setAccountID("zoki123");

        UserResponseDTO response1 = userService.registerUser(user1);
        Mockito.doReturn(Optional.of(new User(user1.getAccountID(), response1.getPassword())))
                .when(userRepository).findUserByUsername(user1.getAccountID());
        assertTrue(response1.isSuccess());
        assertNull(response1.getDescription());
        assertThat(!response1.getPassword().isBlank());


        UserDTO user2 = new UserDTO();
        user2.setAccountID("ugle_18");

        UserResponseDTO response2 = userService.registerUser(user2);
        Mockito.doReturn(Optional.of(new User(user2.getAccountID(), response2.getPassword())))
                .when(userRepository).findUserByUsername(user2.getAccountID());
        assertTrue(response2.isSuccess());
        assertNull(response2.getDescription());
        assertThat(!response2.getPassword().isBlank());
    }

    @Test
    public void registerUserTestFail() {

        UserDTO user1 = new UserDTO();
        user1.setAccountID("tinjara");

        UserResponseDTO response1 = userService.registerUser(user1);
        //Mockito.when(userRepository.findUserByUsername(user1.getAccountID()))
        //        .thenReturn(Optional.of(new User(user1.getAccountID(), response1.getPassword())));
        Mockito.doReturn(Optional.of(new User(user1.getAccountID(), response1.getPassword())))
                .when(userRepository).findUserByUsername(user1.getAccountID());
        assertTrue(response1.isSuccess());
        assertEquals(response1.getDescription(), null);
        assertThat(!response1.getPassword().isBlank());


        UserDTO user2 = new UserDTO();
        user2.setAccountID("tinjara");

        UserResponseDTO response2 = userService.registerUser(user2);
        //Mockito.when(userRepository.findUserByUsername(user2.getAccountID()))
        //        .thenReturn(Optional.of(new User(user2.getAccountID(), response2.getPassword())));
        Mockito.doReturn(Optional.of(new User(user2.getAccountID(), response2.getPassword())))
                .when(userRepository).findUserByUsername(user2.getAccountID());
        assertFalse(response2.isSuccess());
        assertEquals(response2.getDescription(), "Account ID already exists!");
        assertNull(response2.getPassword());
    }

}
