package me.relaxitsdax.thecaverns.game.entities.livingentities.players;

import me.relaxitsdax.thecaverns.game.abilities.players.PlayerAbilityExecutorManager;
import me.relaxitsdax.thecaverns.game.abilities.players.PlayerPassiveAbilityExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        new PlayerPassiveAbilityExecutor(player.getUniqueId());

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

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player.getUniqueId());

        PlayerAbilityExecutorManager.remove(player.getUniqueId());

        PlayerVisualLoopInstanceManager.remove(player.getUniqueId());
        data.getEntityLoop().cancel();
        data.getVisualLoop().cancel();
    }

}
