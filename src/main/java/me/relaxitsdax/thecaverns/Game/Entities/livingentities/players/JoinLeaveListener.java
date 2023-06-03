package me.relaxitsdax.thecaverns.Game.Entities.livingentities.players;

import me.relaxitsdax.thecaverns.Game.Entities.PassiveEntityLoop;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!(PlayerDataManager.contains(player))) {
            PlayerData data = new PlayerData(player.getUniqueId());
            data.getEntityLoop().start();

        } else {
            player.sendMessage("Your data is already in the system!");
            PlayerData data = PlayerDataManager.get(player.getUniqueId());
            PassiveEntityLoop loop = data.getEntityLoop();
            loop.end();
            loop.start();


        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player.getUniqueId());

        data.getEntityLoop().end();
    }

}
