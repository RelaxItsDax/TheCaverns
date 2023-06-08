package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.abilities.Ability;
import me.relaxitsdax.thecaverns.game.abilities.PassiveAbility;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.items.ItemStatBonuses;
import me.relaxitsdax.thecaverns.game.items.Rarity;
import me.relaxitsdax.thecaverns.game.items.StatBonuses;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerGiveCavernItemCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Map<ItemStatBonuses, Double> map = new HashMap<>();
            map.put(ItemStatBonuses.DAMAGE, 100.0);
            map.put(ItemStatBonuses.MAXHEALTH, 900.0);
            StatBonuses bonuses = new StatBonuses(map);

            PassiveAbility[] a = {PassiveAbility.REGENERATION, PassiveAbility.LIFESTEAL, PassiveAbility.GROWTH, PassiveAbility.PHOENIX, null};
            CavernItem item = new CavernItem(UUID.randomUUID(), Material.DIAMOND_SWORD, "Sord", bonuses, Rarity.DIVINE, Ability.HEAL, null, Ability.BARRIER, null, a);
            player.getInventory().addItem(item.getItemStack());

        }

        return true;
    }
}
