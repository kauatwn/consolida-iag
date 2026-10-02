package br.com.consolidaiag.weeklyrequest.domain;

import java.math.BigDecimal;

public record RequestItem(
    Long productId,
    BigDecimal quantity
) {}