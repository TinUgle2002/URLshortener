package com.example.urlshortener.registration.service;

import com.example.urlshortener.registration.DTO.UserDTO;
import com.example.urlshortener.registration.DTO.UserResponseDTO;
import com.example.urlshortener.registration.entity.User;
import com.example.urlshortener.registration.repository.UserRepository;
import com.example.urlshortener.registration.util.PassGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO registerUser(UserDTO user) {

        Optional<User> userByUsername = userRepository.findUserByUsername(user.getAccountID());
        UserResponseDTO responseDTO = new UserResponseDTO();

        if(userByUsername.isPresent()) {
            responseDTO.setSuccess(false);
            responseDTO.setDescription("Account ID already exists!");
        } else {
            responseDTO.setSuccess(true);
            responseDTO.setPassword(PassGenerator.generateRandomPassword(10));
            userRepository.save(new User(user.getAccountID(), responseDTO.getPassword()));
        }

        return responseDTO;
    }
}
