package me.relaxitsdax.thecaverns.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();



        if (!(DataManager.contains(player))) {
            DataManager.add(player.getUniqueId(), new PlayerData(player.getUniqueId()));
        } else {
            player.sendMessage("Your data is already in the system!");
        }

    }

}
