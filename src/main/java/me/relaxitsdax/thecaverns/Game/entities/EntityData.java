package me.relaxitsdax.thecaverns.Game.entities;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class EntityData {


    private final UUID uuid;
    private final Entity entity;
    private PassiveEntityLoop entityLoop;
    private double maxHealth;
    private double health;
    private String nameBar;
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
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);

        EntityDataManager.add(this.getUuid(), this);
        this.entityLoop = new PassiveEntityLoop(uuid);
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
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);

        EntityDataManager.add(this.getUuid(), this);
        this.entityLoop = new PassiveEntityLoop(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }
    public Entity getEntity() {
        return entity;
    }

    public PassiveEntityLoop getEntityLoop() {
        return entityLoop;
    }

    public void newPassiveLoop() {
        this.entityLoop = new PassiveEntityLoop(uuid);
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

    public String getNameBar() {
        return nameBar;
    }

    public void setNameBar(String nameBar) {
        this.nameBar = nameBar;
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

    public void dealDamage(double damage, boolean showTick) {
        damage *= 100 / (this.defense + 100);
        dealTrueDamage(damage, showTick);
    }

    public void dealTrueDamage(double trueDamage, boolean showTick) {
        double finalHealth = this.health;
        if (trueDamage <= barrier) {
            this.barrier -= trueDamage;
        } else {
            this.barrier = 0;
            finalHealth -= (trueDamage - this.barrier);
        }

        if (Math.floor(finalHealth) <= 0) {
            if (entity instanceof Player) {
                this.health = this.maxHealth;
            } else {
                this.health = 0;
            }
                killEntity();
            } else {
            this.health = finalHealth;
        }
        updateEntityHealthBar();

        if (showTick) {

            ArmorStand armorStand = ((ArmorStand) entity.getWorld().spawnEntity(entity.getLocation().add(Util.randNegative() * 0.5 * entity.getWidth(), 0.8 * entity.getHeight(), Util.randNegative() * 0.5 * entity.getWidth()), EntityType.ARMOR_STAND));
            armorStand.setInvisible(true);
            armorStand.setCustomName(ChatColor.GRAY + "" + (int) trueDamage);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    armorStand.remove();
                }
            }.runTaskLater(TheCaverns.getInstance(), 20);
        }
    }

    public void updateEntityHealthBar() {
        this.nameBar = (barrier > 0 ? ChatColor.GOLD : ChatColor.RED) + "❤ " + (int) getEffectiveHealth() + ChatColor.RED + " / " + (int) getMaxHealth() + " ❤";
    }

    public void killEntity() {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.GRAY + "Respawned!", 3, 60, 20);
        } else {
            if (entity instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) entity;
                le.setLastDamageCause(null);
                le.setHealth(0);

            } else {
                entity.remove();
            }
            EntityDataManager.remove(uuid);
            entityLoop.cancel();
            this.nameBar = ChatColor.RED + "❤ 0 / " + (int) getMaxHealth() + " ❤";
        }
    }

}
