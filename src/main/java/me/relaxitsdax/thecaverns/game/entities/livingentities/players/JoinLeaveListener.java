package me.relaxitsdax.thecaverns.game.entities.livingentities.players;

import me.relaxitsdax.thecaverns.game.guis.GUIHandler;
import me.relaxitsdax.thecaverns.game.guis.GUIHandlerManager;
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
            data.reloadPassiveLoop();
            data.reloadVisualLoop();

        } else {
            player.sendMessage("Your data is already in the system!");
            PlayerData data = PlayerDataManager.get(player.getUniqueId());


            data.reloadPassiveLoop();
            data.reloadVisualLoop();
        }

        if (!(GUIHandlerManager.contains(player.getUniqueId()))) {
            GUIHandlerManager.add(player.getUniqueId(), new GUIHandler(player.getUniqueId()));
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player.getUniqueId());

        PlayerVisualLoopInstanceManager.remove(player.getUniqueId());
        data.getEntityLoop().cancel();
        data.getVisualLoop().cancel();
        GUIHandlerManager.remove(player.getUniqueId());
    }

}
