package com.backendproject.BookMyShow.Services;

import com.backendproject.BookMyShow.Entities.User;
import com.backendproject.BookMyShow.Exceptions.InvalidUserException;
import com.backendproject.BookMyShow.Repositories.UserRepository;
import com.backendproject.BookMyShow.Requests.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(AddUserRequest addUserRequest){

        User user = User.builder().emailId(addUserRequest.getEmailId())
                .userName(addUserRequest.getUserName())
                .build();

        userRepository.save(user);
        return "User has been saved to the DB";
    }

    public User getUser(Integer userId) throws Exception{

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new InvalidUserException("User with user id " + userId + " not found");
        }

        User user = userOptional.get();
        return user;
    }
}
