package br.com.consolidaiag.user.adapters.in.web;

import br.com.consolidaiag.shared.adapters.in.security.AuthenticatedUser;
import br.com.consolidaiag.user.application.dtos.request.RegisterUserPayload;
import br.com.consolidaiag.user.application.dtos.response.UserDetailsResponse;
import br.com.consolidaiag.user.application.ports.in.GetCurrentUserUseCase;
import br.com.consolidaiag.user.application.ports.in.ListUsersUseCase;
import br.com.consolidaiag.user.application.ports.in.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final ListUsersUseCase listUsersUseCase;

    public UserController(
            RegisterUserUseCase registerUserUseCase,
            GetCurrentUserUseCase getCurrentUserUseCase,
            ListUsersUseCase listUsersUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<UserDetailsResponse> register(@Valid @RequestBody RegisterUserPayload payload) {
        UserDetailsResponse response = registerUserUseCase.execute(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponse> getMe(
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        return getCurrentUserUseCase.execute(currentUser.uid(), currentUser.email())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<List<UserDetailsResponse>> listAll() {
        return ResponseEntity.ok(listUsersUseCase.execute());
    }
}
