package com.unibuc.finalproject.services.portfolio;

import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;

@Service
public class PortfolioServiceImpl implements PortfolioService {


    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Portfolio findByUser(User user) {
        return portfolioRepository.findPortfolioByUser(user);
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }
}
