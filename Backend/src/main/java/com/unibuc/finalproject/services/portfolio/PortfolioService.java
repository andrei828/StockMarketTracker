package com.unibuc.finalproject.services.portfolio;

import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.user.User;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Port;

@Component
public interface PortfolioService {

    Portfolio findByUser(User user);
    Portfolio save(Portfolio portfolio);

}
