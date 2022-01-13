package com.unibuc.finalproject.models.analyzer;

import javax.validation.constraints.NotNull;

public record AnalyzerResponse(int status, @NotNull String message) {
}
