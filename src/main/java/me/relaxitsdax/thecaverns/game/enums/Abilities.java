package me.relaxitsdax.thecaverns.game.enums;

import me.relaxitsdax.thecaverns.game.abilities.active.ActiveAbility;
import me.relaxitsdax.thecaverns.game.abilities.active.BarrierActiveAbility;
import me.relaxitsdax.thecaverns.game.abilities.active.HealActiveAbility;
import me.relaxitsdax.thecaverns.game.entities.EntityData;

public enum Abilities {
    HEAL("Heal", Rarity.EPIC, 50, 100, new HealActiveAbility(), "a"),
    BARRIER("Barrier", Rarity.LEGENDARY, 20, 100, new BarrierActiveAbility(), "a");


    private final String name;
    private final Rarity rarity;
    private final double manaCost;
    private final int tickCooldown;
    private final String[] lore;
    private final ActiveAbility executor;

    Abilities(String name, Rarity rarity, double manaCost, int tickCooldown, ActiveAbility executor, String... lore) {
        this.name = name;
        this.rarity = rarity;
        this.manaCost = manaCost;
        this.tickCooldown = tickCooldown;
        this.executor = executor;
        this.lore = lore;
    }

    public void execute(EntityData data, Rarity rarity) {
        executor.onUse(data, this, rarity);
    }



    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getTickCooldown() {
        return tickCooldown;
    }

    public String[] getLore() {
        return lore;
    }

}
