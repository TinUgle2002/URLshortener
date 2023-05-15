package com.example.urlshortener;

import com.example.urlshortener.registration.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.Optional;


@SpringBootTest
class UrlshortenerApplicationTests {

    //1.
    //UserServiceTest
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser_whenUsernameAlreadyExists() {
        User existingUser = new User("user1");
        when(userRepository.findUserByUsername("user1")).thenReturn(Optional.of(existingUser));

        User newUser = new User("user1");
        assertThrows(IllegalStateException.class, () -> userService.registerUser(newUser));
    }

    @Test
    public void registerUser_whenUsernameDoesNotExist() {
        when(userRepository.findUserByUsername("user2")).thenReturn(Optional.empty());

        User newUser = new User("user2");
        userService.registerUser(newUser);
    }
    //1.
}
