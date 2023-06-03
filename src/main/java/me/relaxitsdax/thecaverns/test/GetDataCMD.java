package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.Game.Entities.EntityDataManager;
import me.relaxitsdax.thecaverns.Game.Entities.livingentities.players.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetDataCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            PlayerDataManager.printData();
            System.out.println(" --- ");
            EntityDataManager.printData();

        }

        return true;
    }
}
