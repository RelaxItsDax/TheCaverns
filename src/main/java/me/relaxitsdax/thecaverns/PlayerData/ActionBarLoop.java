package me.relaxitsdax.thecaverns.PlayerData;

import me.relaxitsdax.thecaverns.TheCaverns;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class ActionBarLoop {

    private final UUID uuid;
    private BukkitTask barLoop;

    public ActionBarLoop(UUID uuid) {
        this.uuid = uuid;
    }

    public void start() {
        PlayerData data = DataManager.get(uuid);
        Player player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        barLoop = new BukkitRunnable() {
            @Override
            public void run() {

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(build(data)));

            }
        }.runTaskTimer(TheCaverns.getInstance(), 0, 5);
    }

    public void end() {
        this.barLoop.cancel();
    }

    public String build(PlayerData data) {

        Stats health = Stats.HEALTH;
        Stats barrier = Stats.BARRIER;
        Stats defense = Stats.DEFENSE;
        Stats mana = Stats.MANA;
        ChatColor color = (data.getBarrier() == 0 ? health.getColor() :  barrier.getColor());
        String healthNum = color + health.getStatIcon() + " " + (int) data.getEffectiveHealth() + health.getColor() + " / " + (int) data.getMaxHealth() + " " + health.getStatIcon();
        String defenseNum = defense.getColor() + defense.getStatIcon() + " " + (int) data.getDefense() + " " + defense.getStatIcon();
        String manaNum = mana.getColor() + mana.getStatIcon() + " " + (int) data.getMana() + " / " + (int) data.getMaxMana() + " " + mana.getStatIcon();

        return healthNum + "  " + defenseNum + "  " + manaNum;
    }

}
