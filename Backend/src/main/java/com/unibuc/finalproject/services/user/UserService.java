package com.unibuc.finalproject.services.user;

import com.unibuc.finalproject.models.user.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    User findById(Long id);
    User findByEmail(String email);

}
