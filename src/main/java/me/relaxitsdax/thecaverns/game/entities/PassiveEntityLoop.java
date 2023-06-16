package me.relaxitsdax.thecaverns.game.entities;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.enums.Abilities;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PassiveEntityLoop {

    private final UUID uuid;
    private final BukkitTask healthCalc;
    private final BukkitTask manaRegenCalc;
    private final BukkitTask cooldownReduction;


    public PassiveEntityLoop(UUID uuid) {
        this.uuid = uuid;

        TheCaverns INSTANCE = TheCaverns.getInstance();
        EntityData data = EntityDataManager.get(uuid);
        Entity entity = INSTANCE.getServer().getEntity(uuid);

        if (!(PassiveEntityLoopInstanceManager.contains(uuid))) {
            this.healthCalc = new BukkitRunnable() {
                @Override
                public void run() {

                    data.setHealth(Math.min(data.getHealth() + (0.00125 * data.getMaxHealth()), data.getMaxHealth()));

                    if (data.getBarrier() > 0) {
                        data.setBarrier(data.getBarrier() - (0.01 * data.getMaxHealth()));
                    }
                    if (data.getBarrier() <= 0) {
                        data.setBarrier(0);
                    }

                    data.setEffectiveHealth(data.getHealth() + data.getBarrier());

                    if (Math.floor(data.getEffectiveHealth()) > 0) {
                        data.updateEntityHealthBar();
                    }
                    if (!(entity instanceof Player) && entity != null) {
                        entity.setCustomName(entity.getType() + ": " +data.getNameBar());
                        entity.setCustomNameVisible(true);
                    }


                }
            }.runTaskTimer(INSTANCE, 0, 5);

            this.manaRegenCalc = new BukkitRunnable() {
                @Override
                public void run() {
                    data.setMana(Math.min(data.getMana() + 0.0025 * data.getMaxMana(), data.getMaxMana()));
                }
            }.runTaskTimer(INSTANCE, 0, 5);

            this.cooldownReduction = new BukkitRunnable() {
                @Override
                public void run() {
                    if (data != null) data.decrement(5);
                }
            }.runTaskTimer(INSTANCE, 0, 5);

        } else {
            this.healthCalc = null;
            this.manaRegenCalc = null;
            this.cooldownReduction = null;
        }

        PassiveEntityLoopInstanceManager.add(uuid, this);

    }

    public UUID getUuid() {
        return uuid;
    }


    public void cancel() {
        this.healthCalc.cancel();
        this.manaRegenCalc.cancel();
        this.cooldownReduction.cancel();
    }
}
