package me.relaxitsdax.thecaverns.Game.Enemies;

import me.relaxitsdax.thecaverns.PlayerData.PassivePlayerLoop;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class EnemyData {


    private final UUID uuid;
    private final Entity entity;
    private final PassiveEnemyLoop enemyLoop;
    private double maxHealth;
    private double health;
    private double effectiveHealth;
    private double barrier;
    private double defense;
    private double damage;
    private double maxMana;
    private double mana;

    public EnemyData(UUID uuid, Entity entity) {
        this.uuid = uuid;
        this.entity = entity;
        this.maxHealth = 100;
        this.health = 100;
        this.barrier = 0;
        this.defense = 0;
        this.damage = 10;
        this.maxMana = 100;
        this.mana = 100;

        this.effectiveHealth = health + barrier;
        this.enemyLoop = new PassiveEnemyLoop(uuid);

        EnemyDataManager.add(uuid, this);

    }

    public EnemyData(UUID uuid, Entity entity, double maxHealth, double health, double barrier, double defense, double damage, double maxMana, double mana) {
        this.uuid = uuid;
        this.entity = entity;
        this.maxHealth = maxHealth;
        this.health = health;
        this.barrier = barrier;
        this.defense = defense;
        this.damage = damage;
        this.maxMana = maxMana;
        this.mana = mana;

        this.effectiveHealth = health + barrier;
        this.enemyLoop = new PassiveEnemyLoop(uuid);

        EnemyDataManager.add(uuid, this);
    }

    public UUID getUuid() {
        return uuid;
    }
    public Entity getEntity() {
        return entity;
    }

    public PassiveEnemyLoop getEnemyLoop() {
        return enemyLoop;
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
