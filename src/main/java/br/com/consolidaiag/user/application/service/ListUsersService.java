package br.com.consolidaiag.user.application.service;

import br.com.consolidaiag.user.application.dtos.response.UserDetailsResponse;
import br.com.consolidaiag.user.application.ports.in.ListUsersUseCase;
import br.com.consolidaiag.user.application.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListUsersService implements ListUsersUseCase {

    private final UserRepositoryPort repository;

    public ListUsersService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsResponse> execute() {
        return repository.findAll().stream()
                .map(UserDetailsResponse::fromDomain)
                .toList();
    }
}
