package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PassivePlayerLoop {


    private final UUID uuid;
    private BukkitTask effectiveHealthCalc;
    private BukkitTask healthCalc;
    private BukkitTask manaCalc;
    private BukkitTask barrierCalc;
    private BukkitTask healthRegenCalc;
    private BukkitTask actionBarRunnable;

    private BukkitTask manaRegenCalc;

    public PassivePlayerLoop(UUID uuid) {
        this.uuid = uuid;
    }

    public void start() {

        PlayerData data = DataManager.get(this.uuid);
        Player player = TheCaverns.getInstance().getServer().getPlayer(this.uuid);


        this.effectiveHealthCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.setEffectiveHealth(data.getHealth() + data.getBarrier());

                String healthInBar = (data.getBarrier() > 0 ? ChatColor.GOLD : ChatColor.RED) + "" + (int) data.getEffectiveHealth();
                String actionBar = ChatColor.RED + "Health: " + healthInBar + ChatColor.RED + " / " + (int) data.getMaxHealth() + "   " + ChatColor.AQUA + "Mana: " + (int) data.getMana() + " / " + (int) data.getMaxMana();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBar));
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);

        this.healthCalc = new BukkitRunnable() {
            @Override
            public void run() {

                double healthFraction = (data.getHealth() / data.getMaxHealth());
                double hearts = (data.getHealth() == data.getMaxHealth() ? 20 : healthFraction >= 0.1 ? Math.floor(20 * healthFraction) : 1);
                //If health is equal to max health, then set hearts to 20, otherwise if the hearts are less than .5, set hearts to 0.5, otherwise do the hearts calc

                player.setHealth(hearts);


                data.setEffectiveHealth(data.getHealth() + data.getBarrier());
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);

        this.manaCalc = new BukkitRunnable() {
            @Override
            public void run() {
                double manaFraction = data.getMana() / data.getMaxMana();
                player.setExp((float) manaFraction);
                player.setLevel(0);
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);

        this.barrierCalc = new BukkitRunnable() {
            @Override
            public void run() {
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

        this.healthRegenCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.setHealth(Math.min(data.getHealth() + (0.005 * data.getMaxHealth()), data.getMaxHealth()));
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 20);



        this.manaRegenCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.setMana(Math.min(data.getMana() + 0.005 * data.getMaxMana(), data.getMaxMana()));
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);

        this.actionBarRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                String healthInBar = (data.getBarrier() > 0 ? ChatColor.GOLD : ChatColor.RED) + "" + (int) data.getEffectiveHealth();
                String actionBar = ChatColor.RED + "❤ " + healthInBar + ChatColor.RED + "  / " + (int) data.getMaxHealth() + " ❤  " + ChatColor.GREEN + "\uD83D\uDEE1 " + (int) data.getDefense() + " \uD83D\uDEE1  " + ChatColor.AQUA + "★ " + (int) data.getMana() + " / " + (int) data.getMaxMana() + " ★";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBar));
            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);



    }
    public void end() {
        this.healthCalc.cancel();
        this.barrierCalc.cancel();
        this.manaCalc.cancel();
        this.effectiveHealthCalc.cancel();
        this.healthRegenCalc.cancel();
        this.manaRegenCalc.cancel();
        this.actionBarRunnable.cancel();
    }

}
