package me.relaxitsdax.thecaverns.World;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DisablingListeners implements Listener {

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        event.setDamage(0);
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            entity.damage(0, event.getDamager());
        } else {
            event.setCancelled(true);
        }
    }
}
