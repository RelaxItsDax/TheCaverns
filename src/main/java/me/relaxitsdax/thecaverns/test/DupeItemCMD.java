package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.items.CavernWeapon;
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
            CavernWeapon item = new CavernWeapon(player, player.getInventory().getHeldItemSlot());
            player.getInventory().addItem(item.toItemStack());
        }

        return true;
    }
}
