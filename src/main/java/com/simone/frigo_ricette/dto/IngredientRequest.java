package com.simone.frigo_ricette.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IngredientRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    private Short quantity;

    private boolean isAvailable = true;
}
