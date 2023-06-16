package me.relaxitsdax.thecaverns.game.enums;

import me.relaxitsdax.thecaverns.game.abilities.passive.*;
import me.relaxitsdax.thecaverns.game.entities.EntityData;

public enum PassiveAbilities {
    NONE(null, null, null, null),
    LIFESTEAL("Lifesteal", PassiveAbilityProcType.ONHIT, 0, new LifestealPassiveAbility()),
    REGENERATION("Regeneration", PassiveAbilityProcType.WHILEHOLDING, 20, new RegenerationPassiveAbility()),
    GROWTH("Growth", PassiveAbilityProcType.ONKILL, 0, new GrowthPassiveAbility()),
    PHOENIX("Phoenix", PassiveAbilityProcType.ONDEATH, 1200, new PhoenixPassiveAbility()),
    ;

    private final String name;
    private final PassiveAbilityProcType procType;
    private final Integer tickCooldown;
    private final PassiveAbility executor;

    PassiveAbilities(String name, PassiveAbilityProcType procType, Integer tickCooldown, PassiveAbility executor) {
        this.name = name;
        this.procType = procType;
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
}
