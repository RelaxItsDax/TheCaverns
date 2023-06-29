package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.guis.types.createitem.CreateItemInventoryGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateItemCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length > 0) {
                StringBuilder name = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    name.append(args[i]);
                    if (!(i + 1 == args.length)) name.append(" ");
                }
                new CreateItemInventoryGUI(name.toString()).open((Player) sender);
            } else new CreateItemInventoryGUI(null).open((Player) sender);
        }
        return true;
    }
}
