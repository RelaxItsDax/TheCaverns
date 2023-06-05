package me.relaxitsdax.thecaverns.test;

import me.relaxitsdax.thecaverns.Game.entities.EntityData;
import me.relaxitsdax.thecaverns.Game.entities.EntityDataManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class CheckEntityListener implements Listener {

    @EventHandler
    public void onClickEvent(PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();
        Entity clicked = event.getRightClicked();
        if (clicked instanceof Player) player.sendMessage("Please click on a real entity and not a player!");

        EntityData data = EntityDataManager.get(clicked.getUniqueId());

        if (data != null) player.sendMessage("This entity has data!");


    }

}
