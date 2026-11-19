package br.com.consolidaiag.shared.adapters.in.security;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

public record AuthenticatedUser(String uid, String email, ServiceCode serviceCode, String role) {

    public boolean isBuyer() {
        return "BUYER".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role);
    }

    public boolean canAccessService(ServiceCode targetService) {
        if (targetService == null) {
            return false;
        }

        if (isBuyer()) {
            return true;
        }

        return this.serviceCode != null && this.serviceCode == targetService;
    }
}
