package me.relaxitsdax.thecaverns.game.enums;

import me.relaxitsdax.thecaverns.game.abilities.passive.*;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.util.Util;

public enum PassiveAbilities {
    LIFESTEAL("Lifesteal", PassiveAbilityProcType.ONHIT, 10, 0, new LifestealPassiveAbility()),
    REGENERATION("Regeneration", PassiveAbilityProcType.WHILEHOLDING, 10, 20, new RegenerationPassiveAbility()),
    GROWTH("Growth", PassiveAbilityProcType.ONKILL, 10, 0, new GrowthPassiveAbility()),
    PHOENIX("Phoenix", PassiveAbilityProcType.ONDEATH, 10, 1200, new PhoenixPassiveAbility()),
    MANASTEAL("Mana Steal", PassiveAbilityProcType.ONHIT, 10, 0, new ManaStealPassiveAbility()),
    ;

    private final String name;
    private final PassiveAbilityProcType procType;
    private final int weight;
    private final Integer tickCooldown;
    private final PassiveAbility executor;

    PassiveAbilities(String name, PassiveAbilityProcType procType, int weight, Integer tickCooldown, PassiveAbility executor) {
        this.name = name;
        this.procType = procType;
        this.weight = weight;
        this.tickCooldown = tickCooldown;
        this.executor = executor;
    }

    public void execute(EntityData data, Rarity rarity) {
        executor.onProc(data, this, rarity);
    }

    public String getName() {
        return name;
    }

    public PassiveAbilityProcType getProcType() {
        if (this != null) {
            return procType;
        }
        return null;
    }


    public int getTickCooldown() {
        return tickCooldown;
    }

    public int getWeight() {
        return weight;
    }

    public static PassiveAbilities getWeightedRandom() {

        int weightSum = 0;
        for (PassiveAbilities ability : PassiveAbilities.values()) {
            weightSum += ability.getWeight();
        }
        int random = Util.randIntInclusive(0, weightSum);

        int target = 0;
        for (PassiveAbilities ability : PassiveAbilities.values()) {
            target += ability.getWeight();
            if (target >= random) return ability;
        }
        return null;
    }

    public static PassiveAbilities getWeightedRandom(PassiveAbilities exclude) {
        PassiveAbilities passive = getWeightedRandom();
        while (passive == exclude) {
            passive = getWeightedRandom();
            if (passive != exclude) return passive;
        }
        return null;
    }
}
