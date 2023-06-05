package me.relaxitsdax.thecaverns.game.abilities;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.EntityDataManager;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class AbilityExecutor {

    private final UUID uuid;
    private Entity entity;
    private EntityData entityData;

    public AbilityExecutor(UUID uuid) {
        this.uuid = uuid;
        this.entityData = EntityDataManager.get(uuid);
        this.entity = TheCaverns.getInstance().getServer().getEntity(uuid);
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

    public void execute(Abilities ability) {

        switch(ability) {
            case HEAL:
                entityData.heal(10);
                break;
            case BARRIER:
                entityData.addBarrier(20);
                break;
        }
    }



}
