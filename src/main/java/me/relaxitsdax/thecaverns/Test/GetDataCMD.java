package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.Game.Entities.EntityDataManager;
import me.relaxitsdax.thecaverns.Game.Entities.Players.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetDataCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            PlayerDataManager.printData();
            sender.sendMessage("" + EntityDataManager.contains((Player) sender));
            System.out.println(" --- ");
            EntityDataManager.printData();

        }

        return true;
    }
}
