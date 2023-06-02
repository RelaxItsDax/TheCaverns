package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.PlayerData.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DealDamageCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Syntax: /dealdamagetoself <damage (double)> <true>
        //Leave <true> blank if you want normal damage, otherwise put "true"
        //Anything other than "true" will be normal damage

        if (sender instanceof Player) {
            Player player = (Player) sender;

            double damage = Double.parseDouble(args[0]);

            if (args.length > 1) {
                if (args[1].equals("true")) {
                    DataManager.get(player.getUniqueId()).dealTrueDamage(damage);
                }
            } else {
                DataManager.get(player.getUniqueId()).dealDamage(damage);
            }
        }

        return true;
    }
}
