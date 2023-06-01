package me.relaxitsdax.thecaverns.Test;

import me.relaxitsdax.thecaverns.PlayerData.DataManager;
import me.relaxitsdax.thecaverns.PlayerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Test implements Listener {
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) {

        new PlayerData(e.getPlayer().getUniqueId(), 100, 100, 10);

        System.out.println(DataManager.get(e.getPlayer()).getHealth());

    }
}
