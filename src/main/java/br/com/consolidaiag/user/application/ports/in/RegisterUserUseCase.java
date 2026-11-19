package br.com.consolidaiag.user.application.ports.in;

import br.com.consolidaiag.user.application.dtos.request.RegisterUserPayload;
import br.com.consolidaiag.user.application.dtos.response.UserDetailsResponse;

public interface RegisterUserUseCase {

    UserDetailsResponse execute(RegisterUserPayload payload);
}
