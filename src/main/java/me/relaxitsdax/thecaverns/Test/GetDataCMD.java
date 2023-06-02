package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.PlayerData.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetDataCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            DataManager.printData();
            sender.sendMessage("" + DataManager.contains((Player) sender));
        }

        return true;
    }
}
