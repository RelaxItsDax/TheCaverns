package me.relaxitsdax.thecaverns.game.enums;

import me.relaxitsdax.thecaverns.game.abilities.active.BarrierActiveAbility;
import me.relaxitsdax.thecaverns.game.abilities.active.HealActiveAbility;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.util.Util;

public enum ActiveAbility {
    HEAL("Heal", Rarity.EPIC, 5, 50, 100, new HealActiveAbility(), "a"),
    BARRIER("Barrier", Rarity.LEGENDARY, 10, 20, 100, new BarrierActiveAbility(), "a");


    private final String name;
    private final Rarity rarity;
    private final int weight;
    private final double manaCost;
    private final int tickCooldown;
    private final String[] lore;
    private final me.relaxitsdax.thecaverns.game.abilities.active.ActiveAbility executor;

    ActiveAbility(String name, Rarity rarity, int weight, double manaCost, int tickCooldown, me.relaxitsdax.thecaverns.game.abilities.active.ActiveAbility executor, String... lore) {
        this.name = name;
        this.rarity = rarity;
        this.weight = weight;
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

    public int getWeight() {
        return weight;
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


    public static ActiveAbility getWeightedRandom() {

        int weightSum = 0;
        for (ActiveAbility ability : ActiveAbility.values()) {
            weightSum += ability.getWeight();
        }
        int random = Util.randIntInclusive(0, weightSum);

        int target = 0;
        for (ActiveAbility ability : ActiveAbility.values()) {
            target += ability.getWeight();
            if (target >= random) return ability;
        }
        return null;
    }

}
