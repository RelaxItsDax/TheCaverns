package me.relaxitsdax.thecaverns.Game.entities.livingentities.players;

import me.relaxitsdax.thecaverns.Game.entities.livingentities.LivingEntityData;
import me.relaxitsdax.thecaverns.TheCaverns;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class PlayerData extends LivingEntityData {

    private Player player;
    private PlayerVisualLoop loop;
    private final Team team;


    public PlayerData(UUID uuid) {
        super(uuid);

        this.player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        this.team = TheCaverns.getInstance().getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(uuid.toString());
        team.addEntry(player.getDisplayName());

        PlayerDataManager.add(uuid, this);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Base Player Data Made!");
        newVisualLoop();
    }


    public PlayerData(UUID uuid, double maxHealth, double health, double barrier, double defense, double damage, double maxMana, double mana) {
        super(uuid, maxHealth, health, barrier, defense, damage, maxMana, mana);
        this.player = TheCaverns.getInstance().getServer().getPlayer(uuid);

        this.team = TheCaverns.getInstance().getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(uuid.toString());
        team.addEntry(player.getDisplayName());

        PlayerDataManager.add(uuid, this);

        TheCaverns.getInstance().getServer().getPlayer(uuid).sendMessage("Player Data Made!");
        newVisualLoop();
    }

    public PlayerVisualLoop getVisualLoop() {
        return this.loop;
    }

    public void reload() {
        //this.team = TheCaverns.getInstance().getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(getUuid().toString());

        TheCaverns.getInstance().getServer().getPlayer(getUuid()).sendMessage("Data reloaded!");
        PlayerVisualLoopInstanceManager.remove(getUuid());
        player = TheCaverns.getInstance().getServer().getPlayer(getUuid());
        this.loop.cancel();
        this.loop = new PlayerVisualLoop(getUuid());
    }

    public void newVisualLoop() {
        if (PlayerVisualLoopInstanceManager.contains(getUuid())) PlayerVisualLoopInstanceManager.remove(getUuid());
        this.loop = new PlayerVisualLoop(getUuid());
        PlayerVisualLoopInstanceManager.add(getUuid(), this.loop);
    }

    public Player getPlayer() {
        return TheCaverns.getInstance().getServer().getPlayer(getUuid());
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }
}
