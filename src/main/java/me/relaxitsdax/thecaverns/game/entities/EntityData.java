package me.relaxitsdax.thecaverns.game.entities;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilities;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilityProcType;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.enums.ItemStatBonuses;
import me.relaxitsdax.thecaverns.game.items.StatBonuses;
import me.relaxitsdax.thecaverns.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityData {


    private final UUID uuid;
    private Entity entity;
    private PassiveEntityLoop entityLoop;
    private final Map<Abilities, Integer> cooldownMap = new HashMap<>();
    private final Map<PassiveAbilities, Integer> passiveCooldownMap = new HashMap<>();
    private String nameBar;
    private boolean isDead = false;
    private double maxHealth;
    private double baseMaxHealth;
    private double bonusMaxHealth;
    private double health;
    private double effectiveHealth;
    private double barrier;

    private double defense;
    private double baseDefense;
    private double bonusDefense;

    private double damage;
    private double baseDamage;
    private double bonusDamage;
    private double maxMana;
    private double baseMaxMana;
    private double bonusMaxMana;
    private double mana;

    public EntityData(UUID uuid) {
        this.uuid = uuid;
        this.baseMaxHealth = 100;
        this.health = 100;
        this.barrier = 0;
        this.baseDefense = 0;
        this.baseDamage = 10;
        this.baseMaxMana = 100;
        this.mana = 100;

        resetBonuses();

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

        resetBonuses();

        this.effectiveHealth = health + barrier;
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);

        EntityDataManager.add(this.getUuid(), this);
        this.entityLoop = new PassiveEntityLoop(uuid);

    }

    public UUID getUuid() {
        return uuid;
    }
    public Entity getEntity() {
        this.entity = TheCaverns.getInstance().getServer().getEntity(this.uuid);
        return entity;
    }

    public PassiveEntityLoop getEntityLoop() {
        return entityLoop;
    }

    public void reloadPassiveLoop() {
        if (PassiveEntityLoopInstanceManager.contains(getUuid())) PassiveEntityLoopInstanceManager.remove(getUuid());
        this.entityLoop = new PassiveEntityLoop(uuid);
        PassiveEntityLoopInstanceManager.add(getUuid(), this.entityLoop);
    }

    public int getCooldown(Abilities ability) {
        return cooldownMap.getOrDefault(ability, 0);
    }

    public void addCooldown(Abilities ability, int ticks) {
        this.cooldownMap.put(ability, ticks);
    }

    public int getPassiveCooldown(PassiveAbilities ability) {
        return passiveCooldownMap.getOrDefault(ability, 0);
    }

    public void addPassiveCooldown(PassiveAbilities ability, int ticks) {
        this.passiveCooldownMap.put(ability, ticks);
    }

    public void decrement(int ticks) {
        for (Abilities abilities : cooldownMap.keySet()) {
            int cooldown = cooldownMap.get(abilities);
            cooldown -= ticks;
            if (cooldown <= 0) {
                cooldownMap.remove(abilities);
            } else {
                cooldownMap.put(abilities, cooldown);
            }
        }


        for (PassiveAbilities abilities : passiveCooldownMap.keySet()) {
            if (abilities.getTickCooldown() > 0) {

                int cooldown = passiveCooldownMap.get(abilities);
                cooldown -= ticks;
                if (cooldown <= 0) {
                    if (abilities.getProcType() == PassiveAbilityProcType.WHILEHOLDING) {
                        passiveCooldownMap.put(abilities, abilities.getTickCooldown());
                    } else passiveCooldownMap.remove(abilities);
                } else {
                    passiveCooldownMap.replace(abilities, cooldown);
                }
            } else {
                passiveCooldownMap.remove(abilities);
            }
        }
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

    public void addHealth(double health) {
        this.health = Math.min(this.health + health, this.maxHealth);
    }

    public void addPartHealth(double part) {
        addHealth(part * this.maxHealth);
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

    public void addPartBarrier(double part) { //Should be between 0 and 1
        addBarrier(part * maxHealth);
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

    public void useMana(double mana) {
        if (hasMana(mana)) {
            this.mana -= mana;
        }
    }

    public boolean hasMana(double mana) {
        return mana <= this.mana;
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
                Player player =  (Player) entity;
                if (CavernItem.isCavernItem(player.getInventory().getItem(player.getInventory().getHeldItemSlot()))) {
                    CavernItem item = new CavernItem(player, player.getInventory().getHeldItemSlot());
                    for (int i = 0; i < 5; i++) {
                        PassiveAbilities ability = item.getPassiveAbility(i);
                        if (ability != null)
                            if (ability.getProcType() == PassiveAbilityProcType.ONDEATH) ability.execute(this, item.getPassiveAbilityRarity(i));
                    }
                }
            } else {
                this.health = 0;
                this.effectiveHealth = 0;
            }
                killEntity();
                this.isDead = true;
            } else {
            this.health = finalHealth;
        }
        updateEntityHealthBar();

        if (showTick) {
            Entity entity = getEntity();

            ArmorStand armorStand = ((ArmorStand) entity.getWorld().spawnEntity(entity.getLocation().add(Math.cos(Math.toRadians(Util.randIntInclusive(0, 360))), 1 + Math.cos(Math.toRadians(Util.randIntInclusive(0, 360))) / 2, Math.sin(Math.toRadians(Util.randIntInclusive(0, 360)))), EntityType.ARMOR_STAND));
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

    public void setBar(String str) {
        this.nameBar = str;
        entity.setCustomName(entity.getType() + ": " + this.nameBar);
    }

    public void killEntity() {

        if (TheCaverns.getInstance().getServer().getEntity(uuid) instanceof Player)  {
            Player player = (Player) TheCaverns.getInstance().getServer().getEntity(uuid);
            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.GRAY + "Respawned!", 3, 60, 20);
        } else {

            this.nameBar = ChatColor.RED + "❤ 0 / " + (int) getMaxHealth() + " ❤";
            setBar(this.nameBar);
            if (entity instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) entity;
                le.setLastDamageCause(null);
                le.setHealth(0);

            } else {
                entity.remove();
            }
            EntityDataManager.remove(uuid);
            entityLoop.cancel();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void addBonusStats(StatBonuses statBonuses) {
        this.bonusDamage = statBonuses.get(ItemStatBonuses.DAMAGE);
        this.bonusMaxHealth = statBonuses.get(ItemStatBonuses.MAXHEALTH);

        this.damage = baseDamage + bonusDamage;
        this.maxHealth = baseMaxHealth + bonusMaxHealth;
        this.maxMana = baseMaxMana + bonusMaxMana;
    }

    public void resetBonuses() {

        this.damage = baseDamage;
        this.maxHealth = baseMaxHealth;
        this.maxMana = baseMaxMana;

    }
}
