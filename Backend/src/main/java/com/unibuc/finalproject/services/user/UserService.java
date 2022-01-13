package com.unibuc.finalproject.services.user;

import com.unibuc.finalproject.models.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    User findById(Long id);
    User findByEmail(String email);
    User save(User user);
    List<User> findAll();
}
