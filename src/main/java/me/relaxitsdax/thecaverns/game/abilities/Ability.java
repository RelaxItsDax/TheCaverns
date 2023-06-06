package me.relaxitsdax.thecaverns.game.abilities;

import java.util.UUID;

public enum Ability {
    HEAL("Heal", 50),
    BARRIER("Barrier", 50);


    private final String name;
    private final double manaCost;

    Ability(String name, double manaCost) {
        this.name = name;
        this.manaCost = manaCost;
    }


    public void execute(UUID uuid) {
        new AbilityExecutor(uuid).execute(this);
    }

    public String getName() {
        return name;
    }

    public double getManaCost() {
        return manaCost;
    }

}
