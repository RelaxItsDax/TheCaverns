package me.relaxitsdax.thecaverns.game.abilities;

public enum PassiveAbility {
    NONE(null, null, null),
    LIFESTEAL("Lifesteal", PassiveAbilityProcType.ONHIT, 5),
    REGENERATION("Regeneration", PassiveAbilityProcType.WHILEHOLDING, 5),
    GROWTH("Growth", PassiveAbilityProcType.ONKILL, 5),
    PHOENIX("Phoenix", PassiveAbilityProcType.ONDEATH, 1200),
    ;

    private final String name;
    private final PassiveAbilityProcType procType;
    private final Integer tickCooldown;

    PassiveAbility(String name, PassiveAbilityProcType procType, Integer tickCooldown) {
        this.name = name;
        this.procType = procType;
        this.tickCooldown = tickCooldown;
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
