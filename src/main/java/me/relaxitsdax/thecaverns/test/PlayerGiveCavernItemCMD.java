package me.relaxitsdax.thecaverns.test;

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
            map.put(ItemStatBonuses.DAMAGE, 50.0);
            map.put(ItemStatBonuses.MAXHEALTH, 20.0);
            StatBonuses bonuses = new StatBonuses(map);

            CavernItem item = new CavernItem(UUID.randomUUID(), Material.DIAMOND_SWORD, "Sord", bonuses, Rarity.DIVINE, null, null, null, null, null);
            player.getInventory().addItem(item.toItemStack());

        }

        return true;
    }
}
