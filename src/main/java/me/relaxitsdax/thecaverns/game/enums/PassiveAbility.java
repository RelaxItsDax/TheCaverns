package me.relaxitsdax.thecaverns.game.enums;

import me.relaxitsdax.thecaverns.game.abilities.passive.*;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.util.Util;
import org.bukkit.Material;

public enum PassiveAbility {
    LIFESTEAL("Lifesteal", Material.REDSTONE, PassiveAbilityProcType.ONHIT, 10, 0, new LifestealPassiveAbility()),
    REGENERATION("Regeneration", Material.GLISTERING_MELON_SLICE, PassiveAbilityProcType.WHILEHOLDING, 10, 20, new RegenerationPassiveAbility()),
    GROWTH("Growth", Material.GOLDEN_CARROT, PassiveAbilityProcType.ONKILL, 10, 0, new GrowthPassiveAbility()),
    PHOENIX("Phoenix", Material.BLAZE_POWDER, PassiveAbilityProcType.ONDEATH, 10, 1200, new PhoenixPassiveAbility()),
    MANASTEAL("Mana Steal", Material.GLOW_INK_SAC, PassiveAbilityProcType.ONHIT, 10, 0, new ManaStealPassiveAbility()),
    ;

    private final String name;
    private final Material icon;
    private final PassiveAbilityProcType procType;
    private final int weight;
    private final Integer tickCooldown;
    private final me.relaxitsdax.thecaverns.game.abilities.passive.PassiveAbility executor;

    PassiveAbility(String name, Material icon, PassiveAbilityProcType procType, int weight, Integer tickCooldown, me.relaxitsdax.thecaverns.game.abilities.passive.PassiveAbility executor) {
        this.name = name;
        this.icon = icon;
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

    public Material getIcon() {
        return icon;
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

    public static PassiveAbility getWeightedRandom() {

        int weightSum = 0;
        for (PassiveAbility ability : PassiveAbility.values()) {
            weightSum += ability.getWeight();
        }
        int random = Util.randIntInclusive(0, weightSum);

        int target = 0;
        for (PassiveAbility ability : PassiveAbility.values()) {
            target += ability.getWeight();
            if (target >= random) return ability;
        }
        return null;
    }

    public static PassiveAbility getWeightedRandom(PassiveAbility exclude) {
        PassiveAbility passive = getWeightedRandom();
        while (passive == exclude) {
            passive = getWeightedRandom();
            if (passive != exclude) return passive;
        }
        return null;
    }
}
