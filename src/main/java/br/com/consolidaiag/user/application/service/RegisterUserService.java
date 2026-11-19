package br.com.consolidaiag.user.application.service;

import br.com.consolidaiag.user.application.dtos.request.RegisterUserPayload;
import br.com.consolidaiag.user.application.dtos.response.UserDetailsResponse;
import br.com.consolidaiag.user.application.ports.in.RegisterUserUseCase;
import br.com.consolidaiag.user.application.ports.out.UserRepositoryPort;
import br.com.consolidaiag.user.domain.User;
import br.com.consolidaiag.user.domain.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort repository;

    public RegisterUserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetailsResponse execute(RegisterUserPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null.");
        }

        if (repository.findByEmail(payload.email()).isPresent()) {
            throw new UserAlreadyExistsException(payload.email());
        }

        User newUser = User.create(
                payload.firebaseUid(),
                payload.email(),
                payload.name(),
                payload.role(),
                payload.serviceCode()
        );

        User saved = repository.save(newUser);
        return UserDetailsResponse.fromDomain(saved);
    }
}