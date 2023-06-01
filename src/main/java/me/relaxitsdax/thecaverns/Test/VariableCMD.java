package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.PlayerData.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VariableCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            PlayerData playerData = new PlayerData(player.getUniqueId(), 100, 100, 10);

        }



        return true;
    }
}
