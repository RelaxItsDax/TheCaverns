package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.EntityDataManager;
import me.relaxitsdax.thecaverns.TheCaverns;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GiveEnemiesDataCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            for (World world : TheCaverns.getInstance().getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    int count = 0;
                    if (!(EntityDataManager.contains(entity))) {
                        EntityData data = new EntityData(entity.getUniqueId());
                        EntityDataManager.add(entity.getUniqueId(), data);
                        count++;
                    }
                    System.out.println(count + " entities were given data!");
                }
            }
        }

        return true;
    }
}
