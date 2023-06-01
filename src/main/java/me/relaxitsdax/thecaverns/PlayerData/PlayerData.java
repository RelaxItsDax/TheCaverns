package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;
import java.util.Objects;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private double maxHealth;
    private double health;
    private double damage;

    public PlayerData(UUID uuid, double maxHealth, double health, double damage) {
        this.uuid = uuid;
        this.maxHealth = maxHealth;
        this.health = health;
        this.damage = damage;

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Yabadabadoo");
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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
