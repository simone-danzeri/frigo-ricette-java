package com.simone.frigo_ricette.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RecipeRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Process is required")
    private String process;

    @NotNull(message = "Ingredient is required")
    private List<Long> ingredientsIds;
}
