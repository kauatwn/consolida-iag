package br.com.consolidaiag.user.application.ports.out;

import br.com.consolidaiag.user.domain.User;
import br.com.consolidaiag.user.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByFirebaseUid(String firebaseUid);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);
}
