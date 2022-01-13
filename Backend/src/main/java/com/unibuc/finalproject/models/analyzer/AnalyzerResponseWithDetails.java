package com.unibuc.finalproject.models.analyzer;

import java.util.List;

public record AnalyzerResponseWithDetails(Long id, String email, List<String> stockSymbols) {
}
