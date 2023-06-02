package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PassivePlayerLoop {

    public static void start(UUID uuid) {

        PlayerData data = DataManager.get(uuid);
        Player player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        new BukkitRunnable() {
            @Override
            public void run() {

                data.setEffectiveHealth(data.getHealth() + data.getBarrier());



                String healthInBar = (data.getBarrier() > 0 ? ChatColor.GOLD : ChatColor.RED) + "" + (int) data.getEffectiveHealth();
                String actionBar = ChatColor.RED + "Health: " + healthInBar + ChatColor.RED + " / " + (int) data.getMaxHealth() + "   " + ChatColor.AQUA + "Mana: " + (int) data.getMana() + " / " + (int) data.getMaxMana();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBar));

                //Health Calculations
                double healthFraction = (data.getHealth() / data.getMaxHealth());
                player.setHealth(data.getHealth() == data.getMaxHealth() ? 20 : healthFraction >= 0.1 ? Math.floor(20 * healthFraction) : 1);
                //If health is equal to max health, then set hearts to 20, otherwise if the hearts are less than .5, set hearts to 0.5, otherwise do the hearts calc
                data.setEffectiveHealth(data.getHealth() + data.getBarrier());

                //Mana Calculations
                double manaFraction = data.getMana() / data.getMaxMana();
                player.setExp((float) manaFraction);
                player.setLevel(0);


                if (data.getBarrier() > 0) {
                    double barrierFraction = (data.getBarrier() / data.getMaxHealth());
                    player.setAbsorptionAmount(data.getBarrier() == data.getMaxHealth() ? 20 : barrierFraction >= 0.1 ? Math.floor(20 * barrierFraction) : 1);
                } else {
                    player.setAbsorptionAmount(0);
                }

                if (data.getBarrier() > 0) {
                    data.setBarrier(data.getBarrier() - (0.01 * data.getMaxHealth()));
                }
                if (data.getBarrier() <= 0) {
                    data.setBarrier(0);
                }



            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);





    }

}
