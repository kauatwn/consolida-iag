package br.com.consolidaiag.weeklyrequest.application.ports.in;

import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

import java.util.Optional;

public interface ConsultActiveRequestUseCase {
    Optional<WeeklyRequestDetailsResponse> execute(ServiceCode serviceCode);
}