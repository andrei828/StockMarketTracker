package com.unibuc.finalproject.repositories;

import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AnalyzerRepository extends JpaRepository<Analyzer, Object> {
    List<Analyzer> findAnalyzersByUser(User user);
    Analyzer findAnalyzerById(Long id);

    @Transactional
    void deleteAnalyzerById(Long id);
}