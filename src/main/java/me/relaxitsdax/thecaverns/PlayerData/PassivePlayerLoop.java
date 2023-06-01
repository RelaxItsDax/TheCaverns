package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PassivePlayerLoop {

    public static void start(Player player) {

        PlayerData data = DataManager.get(player.getUniqueId());

        new BukkitRunnable() {
            @Override
            public void run() {
                double healthFraction = data.getHealth() / data.getMaxHealth();
                double healthCalc = healthFraction;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Health: " + (int) data.getHealth() + " / " + (int) data.getMaxHealth()));
                player.setHealth(data.getHealth() == data.getMaxHealth() ? 20 : healthFraction >= 0.1 ? Math.floor(20 * healthFraction) : 1);
                //If health is equal to max health, then set hearts to 20, otherwise if the hearts are less than .5, set hearts to 0.5, otherwise do the hearts calc

                double manaFraction = data.getMana() / data.getMaxMana();
                player.setExp((float) manaFraction);
                player.setLevel((int) Math.floor(data.getMana()));
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);





    }

}
