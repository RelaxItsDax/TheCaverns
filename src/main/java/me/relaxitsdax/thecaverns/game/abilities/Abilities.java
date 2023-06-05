package me.relaxitsdax.thecaverns.game.abilities;

import java.util.UUID;

public enum Abilities {
    HEAL("Heal", 50),
    BARRIER("Barrier", 50);


    Abilities(String name, double manaCost) {
    }


    public void execute(UUID uuid) {
        new AbilityExecutor(uuid).execute(this);
    }
}
