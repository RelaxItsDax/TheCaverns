package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.Game.Entities.Players.PlayerData;
import me.relaxitsdax.thecaverns.Game.Entities.Players.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddStatCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage("You need to be a player!");}
        assert sender instanceof Player;
        Player player = (Player) sender;
        PlayerData data = PlayerDataManager.get(player.getUniqueId());
        double number = Integer.parseInt(args[1]);
        assert data != null;

        switch (args[0]) {
            case "barrier":
                data.addBarrier(number);
                break;
        }

        return true;
    }
}
