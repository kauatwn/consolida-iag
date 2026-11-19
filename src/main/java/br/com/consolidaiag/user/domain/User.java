package br.com.consolidaiag.user.domain;

import br.com.consolidaiag.user.domain.exceptions.InvalidUserDataException;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

import java.util.Objects;

public final class User {

    private final UserId id;
    private final String firebaseUid;
    private String email;
    private String name;
    private UserRole role;
    private ServiceCode serviceCode;
    private boolean active;

    private User(UserId id, String firebaseUid, String email, String name, UserRole role, ServiceCode serviceCode, boolean active) {
        if (email == null || email.isBlank()) {
            throw new InvalidUserDataException("O e-mail do usuário é obrigatório.");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidUserDataException("O nome do usuário é obrigatório.");
        }
        if (role == null) {
            throw new InvalidUserDataException("O perfil (role) do usuário é obrigatório.");
        }
        if (role == UserRole.KITCHEN_LEADER && serviceCode == null) {
            throw new InvalidUserDataException("Líder de Cozinha deve estar vinculada a um serviço específico (CEI, CCA ou CJ).");
        }

        this.id = Objects.requireNonNull(id, "ID do usuário é obrigatório.");
        this.firebaseUid = firebaseUid != null ? firebaseUid.trim() : null;
        this.email = email.trim().toLowerCase();
        this.name = name.trim();
        this.role = role;
        this.serviceCode = (role == UserRole.KITCHEN_LEADER) ? serviceCode : null;
        this.active = active;
    }

    public static User create(String firebaseUid, String email, String name, UserRole role, ServiceCode serviceCode) {
        return new User(UserId.generate(), firebaseUid, email, name, role, serviceCode, true);
    }

    public static User reconstitute(UserId id, String firebaseUid, String email, String name, UserRole role, ServiceCode serviceCode, boolean active) {
        return new User(id, firebaseUid, email, name, role, serviceCode, active);
    }

    public void updateDetails(String name, UserRole role, ServiceCode serviceCode) {
        if (name == null || name.isBlank()) {
            throw new InvalidUserDataException("O nome do usuário não pode ser vazio.");
        }
        if (role == null) {
            throw new InvalidUserDataException("O perfil do usuário é obrigatório.");
        }
        if (role == UserRole.KITCHEN_LEADER && serviceCode == null) {
            throw new InvalidUserDataException("Líder de Cozinha deve estar vinculada a um serviço específico (CEI, CCA ou CJ).");
        }

        this.name = name.trim();
        this.role = role;
        this.serviceCode = (role == UserRole.KITCHEN_LEADER) ? serviceCode : null;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isBuyer() {
        return role == UserRole.BUYER;
    }

    public boolean canAccessService(ServiceCode targetService) {
        if (isBuyer()) {
            return true;
        }
        return this.serviceCode != null && this.serviceCode == targetService;
    }

    public UserId getId() {
        return id;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public boolean isActive() {
        return active;
    }
}
