package me.relaxitsdax.thecaverns.game.abilities;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.EntityDataManager;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PassiveAbilityExecutor {

    private final UUID uuid;
    private final Entity entity;
    private final EntityData entityData;
    private final Map<PassiveAbility, Integer> cooldownMap;
    private final Map<PassiveAbility, BukkitTask> taskMap;

    public PassiveAbilityExecutor(UUID uuid) {
        this.uuid = uuid;
        this.entityData = EntityDataManager.get(uuid);
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);
        this.cooldownMap = new HashMap<>();
        this.taskMap = new HashMap<>();


    }

    public void executeNoCD(PassiveAbility passiveAbility) {

        switch (passiveAbility) {
            case LIFESTEAL:
                entityData.addHealth(0.01 * entityData.getMaxHealth());
                break;
            case REGENERATION:
                entityData.addHealth(0.0025 * entityData.getMaxHealth());
                break;
            case GROWTH:
                entityData.addBarrier(20);
                break;
            case PHOENIX:
                entityData.addBarrier(entityData.getMaxHealth());
                break;
        }

    }

    public void execute(PassiveAbility passiveAbility) {

        if (passiveAbility != null) {
            if (!cooldownMap.containsKey(passiveAbility)) {

                executeNoCD(passiveAbility);

                int period = 5;

                cooldownMap.put(passiveAbility, passiveAbility.getTickCooldown());
                taskMap.put(passiveAbility, new BukkitRunnable() {
                    @Override
                    public void run() {

                        cooldownMap.put(passiveAbility, Math.max(cooldownMap.get(passiveAbility) - period, 0));

                    }
                }.runTaskTimer(TheCaverns.getInstance(), 0, period));

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        taskMap.get(passiveAbility).cancel();
                    }
                }.runTaskLater(TheCaverns.getInstance(), passiveAbility.getTickCooldown());

            }
        }
    }
}
