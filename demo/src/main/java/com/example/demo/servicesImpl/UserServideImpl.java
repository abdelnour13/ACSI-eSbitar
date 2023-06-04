package com.example.demo.servicesImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.errors.ResourceNotFoundException;

@Service
public class UserServideImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServideImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Transactional
    @Override
    public User updateUser(Long id,User user) {
        Optional<User> opUser = userRepository.findById(id);
        User _user = null;
        if(opUser.isPresent()) {
            _user = opUser.get();
        } else {
            throw new ResourceNotFoundException("user", "id", id);
        }
        
        if(user.getName()!=null) _user.setName(user.getName());
        if(user.getGender()!=null) _user.setGender(user.getGender());

        userRepository.save(_user);
        
        return _user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

}
