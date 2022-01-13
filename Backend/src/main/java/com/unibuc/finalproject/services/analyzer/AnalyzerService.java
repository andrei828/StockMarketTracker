package com.unibuc.finalproject.services.analyzer;

import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnalyzerService {

    List<Analyzer> findByUser(User user);
    Analyzer save(Analyzer analyzer);
    Analyzer findById(Long id);
    void removeAnalyzerById(Long id);
    void delete(Analyzer analyzer);
}
