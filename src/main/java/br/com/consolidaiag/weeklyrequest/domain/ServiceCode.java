package br.com.consolidaiag.weeklyrequest.domain;

public enum ServiceCode {
    CEI("Centro de Educação Infantil"),
    CCA("Centro para Crianças e Adolescentes"),
    CJ("Centro de Juventude");

    private final String institutionalName;

    ServiceCode(String institutionalName) {
        this.institutionalName = institutionalName;
    }

    public String getInstitutionalName() {
        return institutionalName;
    }
}