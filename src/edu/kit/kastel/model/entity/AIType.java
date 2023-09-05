package edu.kit.kastel.model.entity;

public enum AIType {
    BogoAI("BogoAI"),
    HeroAI("HeroAI");

    private final String name;
    AIType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
