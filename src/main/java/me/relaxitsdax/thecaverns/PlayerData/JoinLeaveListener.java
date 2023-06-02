package me.relaxitsdax.thecaverns.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!(DataManager.contains(player))) {
            PlayerData data = new PlayerData(player.getUniqueId());
            DataManager.add(player.getUniqueId(), data);
            data.getPlayerLoop().start();

        } else {
            player.sendMessage("Your data is already in the system!");
            PassivePlayerLoop loop = DataManager.get(player.getUniqueId()).getPlayerLoop();
            loop.end();
            loop.start();

        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        DataManager.get(player.getUniqueId()).getPlayerLoop().end();
    }

}
