package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ItemPayload(
    @NotNull(message = "O ID do produto é obrigatório")
    Long productId,

    @NotNull(message = "A quantidade é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    BigDecimal quantity
) {}