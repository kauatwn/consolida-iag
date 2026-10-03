package br.com.consolidaiag.catalog.domain;

public enum UnitMeasure {
    KG("Quilograma"),
    L("Litro"),
    UN("Unidade"),
    CX("Caixa"),
    PCT("Pacote"),
    G("Grama"),
    ML("Mililitro");

    private final String description;

    UnitMeasure(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}