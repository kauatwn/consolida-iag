package br.com.consolidaiag.user.adapters.out.persistence;

import br.com.consolidaiag.user.domain.User;
import br.com.consolidaiag.user.domain.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserJpaEntity toJpaEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserJpaEntity(
                user.getId().value(),
                user.getFirebaseUid(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getServiceCode(),
                user.isActive()
        );
    }

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.reconstitute(
                UserId.from(entity.getId()),
                entity.getFirebaseUid(),
                entity.getEmail(),
                entity.getName(),
                entity.getRole(),
                entity.getServiceCode(),
                entity.isActive()
        );
    }
}
