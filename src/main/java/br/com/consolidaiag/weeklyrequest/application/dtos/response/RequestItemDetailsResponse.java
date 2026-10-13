package br.com.consolidaiag.weeklyrequest.application.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record RequestItemDetailsResponse(
        UUID productId,
        BigDecimal quantity
) {
}