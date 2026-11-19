package br.com.consolidaiag.user.adapters.out.persistence;

import br.com.consolidaiag.shared.adapters.out.persistence.AuditableJpaEntity;
import br.com.consolidaiag.user.domain.UserRole;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserJpaEntity extends AuditableJpaEntity {

    @Id
    private UUID id;

    @Column(name = "firebase_uid", length = 128)
    private String firebaseUid;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_code", length = 10)
    private ServiceCode serviceCode;

    @Column(name = "active", nullable = false)
    private boolean active;

    public UserJpaEntity() {
    }

    public UserJpaEntity(UUID id, String firebaseUid, String email, String name, UserRole role, ServiceCode serviceCode, boolean active) {
        this.id = id;
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.name = name;
        this.role = role;
        this.serviceCode = serviceCode;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
