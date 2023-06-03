package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.Game.Entities.EntityDataManager;
import me.relaxitsdax.thecaverns.Game.Entities.Players.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    EntityDataManager.get(player.getUniqueId()).dealTrueDamage(damage);
                }
            } else {
                EntityDataManager.get(player.getUniqueId()).dealDamage(damage);
            }
        }

        return true;
    }
}
