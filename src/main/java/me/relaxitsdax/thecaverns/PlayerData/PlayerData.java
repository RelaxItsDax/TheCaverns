package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private double maxHealth;
    private double health;
    private double effectiveHealth; //health + barrier, subtract from this when dealing normal damage
    private double barrier;
    private double damage;
    private double maxMana;
    private double mana;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.maxHealth = 100;
        this.health = 100;
        this.barrier = 0;
        this.damage = 10;
        this.maxMana = 100;
        this.mana = 100;

        this.effectiveHealth = this.health + this.barrier;

        DataManager.add(uuid, this);

        PassivePlayerLoop.start(uuid);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Base Player Data Made!");
    }

    public PlayerData(UUID uuid, double maxHealth, double health, double barrier, double damage, double maxMana, double mana) {
        this.uuid = uuid;
        this.maxHealth = maxHealth;
        this.health = health;
        this.barrier = barrier;
        this.damage = damage;
        this.maxMana = maxMana;
        this.mana = mana;

        this.effectiveHealth = this.health + this.barrier;

        DataManager.add(uuid, this);

        PassivePlayerLoop.start(uuid);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Player Data Made!");
    }

    public String toString() {
        return "Health: " + this.getHealth() + ", MaxHealth: " + this.maxHealth + ", Damage: " + this.damage;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getBarrier() {
        return barrier;
    }

    public void setBarrier(double barrier) {
        this.barrier = barrier;
    }

    public double getEffectiveHealth() {
        return effectiveHealth;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }
}
