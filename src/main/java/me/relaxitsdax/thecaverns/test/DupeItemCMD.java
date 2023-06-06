package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.items.CavernItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class DupeItemCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            CavernItem item = new CavernItem(player, player.getInventory().getHeldItemSlot());
            player.getInventory().addItem(item.toItemStack());
        }

        return true;
    }
}
