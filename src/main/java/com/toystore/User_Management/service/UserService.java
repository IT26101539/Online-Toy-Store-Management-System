package com.toystore.User_Management.service;

import com.toystore.User_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// COMPONENT 01:User Management | Owner: IT26101544

// Mark this class as a Spring Service component to manage user-related business logic
@Service
public class UserService {

    // Repository dependency to interact with the database/storage layer
    private final UserRepository userRepository;

    // Use constructor-based dependency injection to bring in the UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository =userRepository;
    }

}