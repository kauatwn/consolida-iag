package br.com.consolidaiag.user.domain;

public enum UserRole {
    KITCHEN_LEADER("Líder de Cozinha"),
    BUYER("Gestora / Comprador");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
