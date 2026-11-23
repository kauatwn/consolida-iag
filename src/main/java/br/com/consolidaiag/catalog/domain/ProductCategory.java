package br.com.consolidaiag.catalog.domain;

public enum ProductCategory {
    GRAOS_E_CEREAIS("Grãos e Cereais"),
    PROTEINAS_CARNES("Proteínas e Carnes"),
    HORTIFRUTI_FRESCOS("Hortifrúti e Frescos"),
    LATICINIOS_DERIVADOS("Laticínios e Derivados"),
    MERCEARIA_PANIFICACAO("Mercearia e Panificação");

    private final String description;

    ProductCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}