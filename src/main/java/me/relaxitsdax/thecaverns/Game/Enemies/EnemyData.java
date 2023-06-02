package me.relaxitsdax.thecaverns.Game.Enemies;

import me.relaxitsdax.thecaverns.PlayerData.ActionBarLoop;
import me.relaxitsdax.thecaverns.PlayerData.PassivePlayerLoop;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class EnemyData {


    private final UUID uuid;
    private double maxHealth;
    private double health;
    private double barrier;
    private double defense;
    private double damage;
    private double maxMana;
    private double mana;

    public EnemyData(UUID uuid) {
        this.uuid = uuid;
        this.maxHealth = 100;
        this.health = 100;
        this.barrier = 0;
        this.defense = 0;
        this.damage = 10;
        this.maxMana = 100;
        this.mana = 100;
    }

    public EnemyData(UUID uuid, double maxHealth, double health, double barrier, double defense, double damage, double maxMana, double mana) {
        this.uuid = uuid;
        this.maxHealth = maxHealth;
        this.health = health;
        this.barrier = barrier;
        this.defense = defense;
        this.damage = damage;
        this.maxMana = maxMana;
        this.mana = mana;
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

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
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
