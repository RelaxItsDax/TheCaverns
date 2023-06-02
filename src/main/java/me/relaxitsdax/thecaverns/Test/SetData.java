package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.PlayerData.DataManager;
import me.relaxitsdax.thecaverns.PlayerData.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetData implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage("You need to be a player!");}

        assert sender instanceof Player;
        Player player = (Player) sender;
        PlayerData data = DataManager.get(player.getUniqueId());
        double number = Integer.parseInt(args[1]);
        assert data != null;

        switch (args[0]) {
            case "health":
                data.setHealth(number);
                break;
            case "maxhealth":
                data.setMaxHealth(number);
                break;
            case "barrier":
                data.setBarrier(number);
            case "damage":
                data.setDamage(number);
                break;
            case "maxmana":
                data.setMaxMana(number);
                break;
            case "mana":
                data.setMana(number);
                break;

        }

        return true;
    }
}
