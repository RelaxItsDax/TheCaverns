package me.relaxitsdax.thecaverns.game.world;

import me.relaxitsdax.thecaverns.game.enums.PassiveAbility;
import me.relaxitsdax.thecaverns.game.enums.PassiveAbilityProcType;
import me.relaxitsdax.thecaverns.game.entities.EntityData;
import me.relaxitsdax.thecaverns.game.entities.EntityDataManager;
import me.relaxitsdax.thecaverns.game.items.CavernItem;
import me.relaxitsdax.thecaverns.game.items.CavernWeapon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageEventHandlers implements Listener {

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        event.setDamage(0);

        if (EntityDataManager.contains(entity) && EntityDataManager.contains(damager)) {

            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) event.getEntity();
                livingEntity.damage(0);
            } else {
                event.setCancelled(true);
            }

            EntityData targetData = EntityDataManager.get(entity.getUniqueId());
            EntityData damagerData = EntityDataManager.get(damager.getUniqueId());


            targetData.dealDamage(damagerData.getDamage(), true);


            if (damager instanceof Player) {
                Player player = (Player) damager;
                ItemStack stack = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
                if (CavernWeapon.isCavernWeapon(stack)) {
                    CavernWeapon item = new CavernWeapon(player, player.getInventory().getHeldItemSlot());

                    for (int i = 0; i < 5; i++) {
                        PassiveAbility ability =  item.getPassiveAbility(i);
                        if (ability != null) if (ability.getProcType() == PassiveAbilityProcType.ONHIT) ability.execute(damagerData, item.getPassiveRarity(i));
                    }

                    if (targetData.isDead()) {
                        for (int i = 0; i < 5; i++) {
                            PassiveAbility ability =  item.getPassiveAbility(i);
                            if (ability != null) if (ability.getProcType() == PassiveAbilityProcType.ONKILL) ability.execute(damagerData, item.getPassiveRarity(i));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            event.setCancelled(true);
        }
    }
}
