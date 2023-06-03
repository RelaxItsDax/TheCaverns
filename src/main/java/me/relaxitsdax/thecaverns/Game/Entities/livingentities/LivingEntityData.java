package me.relaxitsdax.thecaverns.Game.Entities.livingentities;

import me.relaxitsdax.thecaverns.Game.Entities.EntityData;
import me.relaxitsdax.thecaverns.Game.Entities.livingentities.players.PlayerDataManager;

import java.util.UUID;

public class LivingEntityData extends EntityData {
    public LivingEntityData(UUID uuid) {
        super(uuid);

        LivingEntityManager.add(uuid, this);
    }

    public LivingEntityData(UUID uuid, double maxHealth, double health, double barrier, double defense, double damage, double maxMana, double mana) {
        super(uuid, maxHealth, health, barrier, defense, damage, maxMana, mana);

        LivingEntityManager.add(uuid, this);
    }
}
