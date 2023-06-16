package me.relaxitsdax.thecaverns.game.abilities.active;

import me.relaxitsdax.thecaverns.TheCaverns;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.EntityDataManager;
import me.relaxitsdax.thecaverns.game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.game.enums.Abilities;
import me.relaxitsdax.thecaverns.game.enums.AbilityStatus;
import me.relaxitsdax.thecaverns.game.enums.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActiveAbility {
    public ActiveAbility() {
    }

    public void onUse(EntityData data, Abilities ability, Rarity abilityRarity) {
    }

    public AbilityStatus getStatus(EntityData data, Abilities ability) {
        AbilityStatus status;
        if (data.getMana() >= ability.getManaCost()) {
            int cooldown = data.getCooldown(ability);
            if (cooldown <= 0) {
                status = AbilityStatus.SUCCESS;
            } else {
                status = AbilityStatus.ONCOOLDOWN;
            }
        } else {
            status = AbilityStatus.NOTENOUGHMANA;
        }

        return status;
    }

    public void sendPlayerMessage(PlayerData data, Abilities ability, AbilityStatus status, Rarity abilityRarity) {
        Player player = TheCaverns.getInstance().getServer().getPlayer(data.getUuid());

        if (player != null) {

            switch (status) {
                case SUCCESS:
                    player.sendMessage(ChatColor.WHITE + "Used " + abilityRarity.getColor() +  ability.getName() + " " + ChatColor.AQUA + "(" + (int) ability.getManaCost() + " Mana)");
                    break;
                case NOTENOUGHMANA:
                    player.sendMessage(ChatColor.RED + "Not enough mana!");
                    break;
                case ONCOOLDOWN:
                    player.sendMessage(ChatColor.RED + "On Cooldown: " + ((data.getCooldown(ability) + 15) / 20) + "s");
                    break;
                case CANNOTUSE:
                    player.sendMessage(ChatColor.RED + "You can't use this here!");
                    break;
            }
        }
    }
}
