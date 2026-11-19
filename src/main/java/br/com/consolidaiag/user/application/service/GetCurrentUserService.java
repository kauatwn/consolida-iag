package br.com.consolidaiag.user.application.service;

import br.com.consolidaiag.user.application.dtos.response.UserDetailsResponse;
import br.com.consolidaiag.user.application.ports.in.GetCurrentUserUseCase;
import br.com.consolidaiag.user.application.ports.out.UserRepositoryPort;
import br.com.consolidaiag.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetCurrentUserService implements GetCurrentUserUseCase {

    private final UserRepositoryPort repository;

    public GetCurrentUserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDetailsResponse> execute(String uid, String email) {
        Optional<User> userOpt = Optional.empty();

        if (email != null && !email.isBlank()) {
            userOpt = repository.findByEmail(email);
        }

        if (userOpt.isEmpty() && uid != null && !uid.isBlank()) {
            userOpt = repository.findByFirebaseUid(uid);
        }

        return userOpt.map(UserDetailsResponse::fromDomain);
    }
}