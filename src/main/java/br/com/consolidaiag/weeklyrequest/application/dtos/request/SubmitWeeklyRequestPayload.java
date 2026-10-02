package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SubmitWeeklyRequestPayload(
    @NotNull(message = "O código do serviço é obrigatório")
    ServiceCode serviceCode,

    String observation,

    @NotEmpty(message = "A solicitação deve conter pelo menos um item")
    @Valid
    List<ItemPayload> items
) {}