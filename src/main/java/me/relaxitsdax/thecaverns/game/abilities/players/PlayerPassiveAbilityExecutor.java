package me.relaxitsdax.thecaverns.game.abilities.players;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.abilities.PassiveAbility;
import me.relaxitsdax.thecaverns.game.abilities.PassiveAbilityExecutor;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerDataManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerPassiveAbilityExecutor extends PassiveAbilityExecutor {

    private Player player;
    private PlayerData playerData;

    public PlayerPassiveAbilityExecutor(UUID uuid) {
        super(uuid);
        this.player = TheCaverns.getInstance().getServer().getPlayer(uuid);
        this.playerData = PlayerDataManager.get(uuid);
    }

    public void playerExecute(PassiveAbility ability) {
        super.execute(ability);
    }


}
