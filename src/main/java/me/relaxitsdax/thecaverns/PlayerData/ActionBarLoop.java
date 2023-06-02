package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class ActionBarLoop {

    private final UUID uuid;
    private BukkitTask barLoop;

    public ActionBarLoop(UUID uuid) {
        this.uuid = uuid;
    }

    public void start() {
        PlayerData data = DataManager.get(uuid);
        Player player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        barLoop = new BukkitRunnable() {
            @Override
            public void run() {

                String healthInBar = (data.getBarrier() > 0 ? ChatColor.GOLD : ChatColor.RED) + "" + (int) data.getEffectiveHealth();
                String actionBar = ChatColor.RED + "❤ " + healthInBar + ChatColor.RED + "  / " + (int) data.getMaxHealth() + " ❤  " + ChatColor.GREEN + "\uD83D\uDEE1 " + (int) data.getDefense() + " \uD83D\uDEE1  " + ChatColor.AQUA + "★ " + (int) data.getMana() + " / " + (int) data.getMaxMana() + " ★";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBar));

            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);
    }

    public void end() {
        this.barLoop.cancel();
    }

    public String build() {
        String str = "";


        return str;
    }

}
