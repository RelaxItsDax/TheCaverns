package me.relaxitsdax.thecaverns.Game.Entities;

import me.relaxitsdax.thecaverns.TheCaverns;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class EntityData {


    private final UUID uuid;
    private final Entity entity;
    private final PassiveEntityLoop enemyLoop;
    private double maxHealth;
    private double health;
    private double effectiveHealth;
    private double barrier;
    private double defense;
    private double damage;
    private double maxMana;
    private double mana;

    public EntityData(UUID uuid) {
        this.uuid = uuid;
        this.maxHealth = 100;
        this.health = 100;
        this.barrier = 0;
        this.defense = 0;
        this.damage = 10;
        this.maxMana = 100;
        this.mana = 100;

        this.effectiveHealth = health + barrier;
        this.enemyLoop = new PassiveEntityLoop(uuid);

        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);

        EntityDataManager.add(this.getUuid(), this);

    }

    public EntityData(UUID uuid, double maxHealth, double health, double barrier, double defense, double damage, double maxMana, double mana) {
        this.uuid = uuid;
        this.maxHealth = maxHealth;
        this.health = health;
        this.barrier = barrier;
        this.defense = defense;
        this.damage = damage;
        this.maxMana = maxMana;
        this.mana = mana;

        this.effectiveHealth = health + barrier;
        this.enemyLoop = new PassiveEntityLoop(uuid);

        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);

        EntityDataManager.add(this.getUuid(), this);
    }

    public UUID getUuid() {
        return uuid;
    }
    public Entity getEntity() {
        return entity;
    }

    public PassiveEntityLoop getEnemyLoop() {
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

    public double getEffectiveHealth() {
        return effectiveHealth;
    }

    public void setEffectiveHealth(double effectiveHealth) {
        this.effectiveHealth = effectiveHealth;
    }

    public double getBarrier() {
        return barrier;
    }

    public void setBarrier(double barrier) {
        this.barrier = barrier;
    }

    public void addBarrier(double barrier) {
        this.barrier = Math.min(this.barrier + barrier + (0.01 * getMaxHealth()), maxHealth + (0.01 * getMaxHealth()));
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

    public void dealDamage(double damage) {
        damage *= 100 / (this.defense + 100);

        double finalHealth = this.health;
        if (damage <= this.barrier) {
            this.barrier -= damage;
        } else {
            this.barrier = 0;
            finalHealth -= (damage - this.barrier);
        }

        if (finalHealth <= 0) {
            this.health = this.maxHealth;
            TheCaverns.getInstance().getServer().getPlayer(this.uuid).sendMessage("You died!");
        } else {
            this.health = finalHealth;
        }
    }

    public void dealTrueDamage(double trueDamage) {
        double finalHealth = this.health;
        if (trueDamage <= barrier) {
            this.barrier -= trueDamage;
        } else {
            this.barrier = 0;
            finalHealth -= (trueDamage - this.barrier);
        }

        if (finalHealth <= 0) {
            this.health = this.maxHealth;
            TheCaverns.getInstance().getServer().getPlayer(this.uuid).sendMessage("You died!");
        } else {
            this.health = finalHealth;
        }
    }
}
