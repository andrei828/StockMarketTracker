package com.unibuc.finalproject.models.analyzer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record AnalyzerListRequest(@NotNull @Size(min = 3, max = 50) String email) {
}
