package me.relaxitsdax.thecaverns.Game.Enemies;

import me.relaxitsdax.thecaverns.TheCaverns;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PassiveEnemyLoop {

    private final UUID uuid;
    private BukkitTask passive;
    private BukkitTask effectiveHealthCalc;
    private BukkitTask healthCalc;
    private BukkitTask barrierCalc;
    private BukkitTask healthRegenCalc;
    private BukkitTask manaRegenCalc;

    public PassiveEnemyLoop(UUID uuid) {
        this.uuid = uuid;
    }


    public void start() {
        
        TheCaverns INSTANCE = TheCaverns.getInstance();

        EnemyData data = EnemyDataManager.get(uuid);
        Entity entity = data.getEntity();
        
        this.passive = new BukkitRunnable() {
            @Override
            public void run() {

                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    livingEntity.setHealth(100000);
                }


            }
        }.runTaskTimer(INSTANCE, 0, 5);
        
        this.effectiveHealthCalc = new BukkitRunnable() {
            @Override
            public void run() {
                
            }
        }.runTaskTimer(INSTANCE, 0, 5);

        this.healthCalc = new BukkitRunnable() {
            @Override
            public void run() {

                entity.setCustomName(ChatColor.RED + "❤ " + (int) data.getHealth() + " / " + (int) data.getMaxHealth() + " ❤");
                entity.setCustomNameVisible(true);

            }
        }.runTaskTimer(INSTANCE, 0, 5);

        this.barrierCalc = new BukkitRunnable() {
            @Override
            public void run() {
                if (data.getBarrier() > 0) {
                    data.setBarrier(data.getBarrier() - (0.01 * data.getMaxHealth()));
                }
                if (data.getBarrier() <= 0) {
                    data.setBarrier(0);
                }
            }
        }.runTaskTimer(INSTANCE, 0, 5);

        this.healthRegenCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.setHealth(Math.min(data.getHealth() + (0.005 * data.getMaxHealth()), data.getMaxHealth()));
            }
        }.runTaskTimer(INSTANCE, 0, 5);

        this.manaRegenCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.setMana(Math.min(data.getMana() + 0.005 * data.getMaxMana(), data.getMaxMana()));
            }
        }.runTaskTimer(INSTANCE, 0, 5);



    }


}
