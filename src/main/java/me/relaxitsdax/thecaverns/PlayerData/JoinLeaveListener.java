package me.relaxitsdax.thecaverns.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.swing.*;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!(DataManager.contains(player))) {
            PlayerData data = new PlayerData(player.getUniqueId());
            DataManager.add(player.getUniqueId(), data);
            data.getPlayerLoop().start();
            data.getActionBarLoop().start();

        } else {
            player.sendMessage("Your data is already in the system!");
            PlayerData data = DataManager.get(player.getUniqueId());
            PassivePlayerLoop loop = data.getPlayerLoop();
            ActionBarLoop barLoop = data.getActionBarLoop();
            loop.end();
            barLoop.end();
            loop.start();
            barLoop.start();


        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = DataManager.get(player.getUniqueId());

        data.getPlayerLoop().end();
        data.getActionBarLoop().end();
    }

}
