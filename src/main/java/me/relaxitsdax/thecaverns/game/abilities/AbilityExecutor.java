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

public class AbilityExecutor {

    private final UUID uuid;
    private Entity entity;
    private EntityData entityData;
    private Map<Ability, Integer> cooldownMap;
    private final Map<Ability, BukkitTask> taskMap;

    public AbilityExecutor(UUID uuid) {
        this.uuid = uuid;
        this.entityData = EntityDataManager.get(uuid);
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);
        this.cooldownMap = new HashMap<>();
        this.taskMap = new HashMap<>();

        for (Ability abilities : Ability.values()) {
            if (!cooldownMap.containsKey(abilities)) cooldownMap.put(abilities, 0);
        }
    }

    public int abilityOnCooldown(Ability ability) {
        return cooldownMap.get(ability);
    }


    public void executeNoCD(Ability ability) {
        if (entityData.hasMana(ability.getManaCost())) {
            entityData.useMana(Ability.BARRIER.getManaCost());
            switch (ability) {
                case HEAL:
                    entityData.addHealth(10);
                    break;

                case BARRIER:
                    entityData.addBarrier(20);
                    break;
            }
        }
    }


    public void execute(Ability ability) {
        if (ability != null) {
            if (entityData.hasMana(ability.getManaCost())) {
                executeNoCD(ability);

                int period = 5;

                cooldownMap.put(ability, ability.getTickCooldown());
                taskMap.put(ability, new BukkitRunnable() {
                    @Override
                    public void run() {
                        int time = Math.max(cooldownMap.get(ability) - period, 0);
                        cooldownMap.put(ability, time);
                        if (time == 0) cancel();

                    }
                }.runTaskTimer(TheCaverns.getInstance(), 0, period));
            }


        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public Entity getEntity() {
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);
        return entity;
    }

    public EntityData getEntityData() {
        this.entityData = EntityDataManager.get(uuid);
        return entityData;
    }
}
