package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.Game.entities.EntityData;
import me.relaxitsdax.thecaverns.Game.entities.EntityDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDataCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage("You need to be a player!");}

        assert sender instanceof Player;
        Player player = (Player) sender;
        EntityData data = EntityDataManager.get(player.getUniqueId());
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
                break;
            case "defense":
                data.setDefense(number);
                break;
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
