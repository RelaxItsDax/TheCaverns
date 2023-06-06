package me.relaxitsdax.thecaverns.game.abilities;

public enum PassiveAbility {
    LIFESTEAL("Lifesteal", PassiveAbilityProcType.ONHIT, 20),
    REGENERATION("Regeneration", PassiveAbilityProcType.WHILEHOLDING, 5),
    ;

    private final String name;
    private final PassiveAbilityProcType procType;
    private final double tickCooldown;

    PassiveAbility(String name, PassiveAbilityProcType procType, double cooldown) {
        this.name = name;
        this.procType = procType;
        this.tickCooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public PassiveAbilityProcType getProcType() {
        return procType;
    }

    public double getTickCooldown() {
        return tickCooldown;
    }
}
