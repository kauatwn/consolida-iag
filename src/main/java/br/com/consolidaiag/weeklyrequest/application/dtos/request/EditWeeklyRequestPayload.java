package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record EditWeeklyRequestPayload(
        String observation,

        @NotEmpty(message = "A solicitação deve conter pelo menos um item")
        List<@Valid ItemPayload> items
) {
}