package me.relaxitsdax.thecaverns.Game.Entities.Players;

import me.relaxitsdax.thecaverns.Game.Entities.EntityData;
import me.relaxitsdax.thecaverns.TheCaverns;

import java.util.UUID;

public class PlayerData extends EntityData {

    private final PlayerBarLoop actionBarLoop;

    public PlayerData(UUID uuid) {
        super(uuid);

        super.setEffectiveHealth(super.getHealth() + super.getBarrier());
        this.actionBarLoop = new PlayerBarLoop(uuid);

        PlayerDataManager.add(uuid, this);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Base Player Data Made!");
    }

    public PlayerData(UUID uuid, double maxHealth, double health, double barrier, double defense, double damage, double maxMana, double mana) {
        super(uuid, maxHealth, health, barrier, defense, damage, maxMana, mana);

        super.setEffectiveHealth(super.getHealth() + super.getBarrier());
        this.actionBarLoop = new PlayerBarLoop(uuid);

        PlayerDataManager.add(uuid, this);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Player Data Made!");
    }

    public PlayerBarLoop getActionBarLoop() {
        return actionBarLoop;
    }
}
