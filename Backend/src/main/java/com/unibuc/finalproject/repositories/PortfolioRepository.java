package com.unibuc.finalproject.repositories;

import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Object> {

    public Portfolio findPortfolioByUser(User user);
}
