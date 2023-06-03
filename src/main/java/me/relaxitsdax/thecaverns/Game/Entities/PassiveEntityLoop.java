package me.relaxitsdax.thecaverns.Game.Entities;

import me.relaxitsdax.thecaverns.Game.Entities.livingentities.players.Stats;
import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PassiveEntityLoop {

    private final UUID uuid;
    private BukkitTask healthNameCalc;
    private BukkitTask healthCalc;
    private BukkitTask playerVisualLoop;

    private BukkitTask manaRegenCalc;


    public PassiveEntityLoop(UUID uuid) {
        this.uuid = uuid;
    }


    public void start() {

        TheCaverns INSTANCE = TheCaverns.getInstance();

        EntityData data = EntityDataManager.get(uuid);
        Entity entity = data.getEntity();

        this.healthNameCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.updateEntityHealthBar();
            }
        }.runTaskTimer(INSTANCE, 0, 5);

        this.healthCalc = new BukkitRunnable() {
            @Override
            public void run() {

                data.setHealth(Math.min(data.getHealth() + (0.00125 * data.getMaxHealth()), data.getMaxHealth()));

                if (data.getBarrier() > 0) {
                    data.setBarrier(data.getBarrier() - (0.01 * data.getMaxHealth()));
                }
                if (data.getBarrier() <= 0) {
                    data.setBarrier(0);
                }

                data.setEffectiveHealth(data.getHealth() + data.getBarrier());


            }
        }.runTaskTimer(INSTANCE, 0, 5);

        this.manaRegenCalc = new BukkitRunnable() {
            @Override
            public void run() {
                data.setMana(Math.min(data.getMana() + 0.0025 * data.getMaxMana(), data.getMaxMana()));
            }
        }.runTaskTimer(INSTANCE, 0, 5);


        if (entity instanceof Player) {
            Player player = (Player) entity;
            this.playerVisualLoop = new BukkitRunnable() {
                @Override
                public void run() {

                    double manaFraction = data.getMana() / data.getMaxMana();
                    player.setExp((float) manaFraction);
                    player.setLevel(0);

                    double healthFraction = (data.getHealth() / data.getMaxHealth());
                    double hearts = (data.getHealth() == data.getMaxHealth() ? 20 : healthFraction >= 0.1 ? Math.floor(20 * healthFraction) : 1);
                    //If health is equal to max health, then set hearts to 20, otherwise if the hearts are less than .5, set hearts to 0.5, otherwise do the hearts calc
                    player.setHealth(hearts);


                    if (data.getBarrier() > 0) {
                        double barrierFraction = (data.getBarrier() / data.getMaxHealth());
                        player.setAbsorptionAmount(data.getBarrier() == data.getMaxHealth() ? 20 : barrierFraction >= 0.1 ? Math.floor(20 * barrierFraction) : 1);
                    } else {
                        player.setAbsorptionAmount(0);
                    }

                    Stats health = Stats.HEALTH;
                    Stats barrier = Stats.BARRIER;
                    Stats defense = Stats.DEFENSE;
                    Stats mana = Stats.MANA;
                    ChatColor color = (data.getEffectiveHealth() - data.getHealth() == 0 ? health.getColor() : barrier.getColor());
                    String healthNum = color + health.getStatIcon() + " " + (int) data.getEffectiveHealth() + health.getColor() + " / " + (int) data.getMaxHealth() + " " + health.getStatIcon();
                    String defenseNum = defense.getColor() + defense.getStatIcon() + " " + (int) data.getDefense() + " " + defense.getStatIcon();
                    String manaNum = mana.getColor() + mana.getStatIcon() + " " + (int) data.getMana() + " / " + (int) data.getMaxMana() + " " + mana.getStatIcon();

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(healthNum + "  " + defenseNum + "  " + manaNum));


                }
            }.runTaskTimer(INSTANCE, 0, 5);
        }

    }

    public void end() {
        this.healthNameCalc.cancel();
        this.healthCalc.cancel();
        this.manaRegenCalc.cancel();
        if (EntityDataManager.get(uuid) instanceof Player) {
            this.playerVisualLoop.cancel();
        }
    }


}
