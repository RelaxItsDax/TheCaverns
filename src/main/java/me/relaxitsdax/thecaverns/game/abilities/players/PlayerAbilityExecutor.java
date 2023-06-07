package me.relaxitsdax.thecaverns.game.abilities.players;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.abilities.Ability;
import me.relaxitsdax.thecaverns.game.abilities.AbilityExecutor;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerAbilityExecutor extends AbilityExecutor {

    private Player player;
    private PlayerData playerData;

    public PlayerAbilityExecutor(UUID uuid) {
        super(uuid);
        this.player = TheCaverns.getInstance().getServer().getPlayer(uuid);
        this.playerData = PlayerDataManager.get(uuid);
        PlayerAbilityExecutorManager.add(uuid, this);
    }

    public void playerExecute(Ability ability) {
        if (playerData.hasMana(ability.getManaCost())) {
            if (abilityOnCooldown(ability) == 0) {
                super.execute(ability);
                player.sendMessage("Used: " + ability.getName() + ChatColor.AQUA + " (" + ability.getManaCost() + " Mana)");
            } else {
                player.sendMessage(ChatColor.RED + "Cooldown: " + (int) Math.ceil((double) abilityOnCooldown(ability) / 20) + "s");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Not enough mana! " + (int) playerData.getMana() + "/" + (int) ability.getManaCost());
        }
    }

}
