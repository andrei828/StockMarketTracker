package com.unibuc.finalproject.services.analyzer;

import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.repositories.AnalyzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyzerServiceImpl implements AnalyzerService {

    @Autowired
    private AnalyzerRepository analyzerRepository;

    @Override
    public List<Analyzer> findByUser(User user) {
        return analyzerRepository.findAnalyzersByUser(user);
    }

    @Override
    public Analyzer save(Analyzer analyzer) {
        return analyzerRepository.save(analyzer);
    }

    @Override
    public Analyzer findById(Long id) {
        return analyzerRepository.findAnalyzerById(id);
    }

    @Override
    public void removeAnalyzerById(Long id) {
        analyzerRepository.deleteById(id);
    }
}
