package me.relaxitsdax.thecaverns;

import me.relaxitsdax.thecaverns.Game.entities.livingentities.LivingEntityData;
import me.relaxitsdax.thecaverns.Game.entities.livingentities.players.PlayerDataManager;
import me.relaxitsdax.thecaverns.Game.entities.livingentities.players.JoinLeaveListener;
import me.relaxitsdax.thecaverns.Game.entities.livingentities.players.PlayerData;
import me.relaxitsdax.thecaverns.test.*;
import me.relaxitsdax.thecaverns.world.DamageEventHandlers;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public final class TheCaverns extends JavaPlugin {


    public void onEnable() {

        INSTANCE = this;
        playerDataManager = new PlayerDataManager();

        //TEAM RESET LOGIC
        for (Team team : getServer().getScoreboardManager().getMainScoreboard().getTeams()) {
            team.unregister();
        }


        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DamageEventHandlers(), this);
        getServer().getPluginManager().registerEvents(new CheckEntityListener(), this);

        getCommand("giveentitiesdata").setExecutor(new GiveEnemiesDataCMD());
        getCommand("dealdamagetoself").setExecutor(new DealDamageCMD());
        getCommand("getdata").setExecutor(new GetDataCMD());
        getCommand("setdata").setExecutor(new SetDataCMD());
        getCommand("adddata").setExecutor(new AddStatCMD());


        for (Player player : getServer().getOnlinePlayers()) {
            PlayerData data = new PlayerData(player.getUniqueId());
        }

        for (World world : getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof LivingEntity) {
                    LivingEntityData data = new LivingEntityData(entity.getUniqueId());
                }
            }
        }

    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

    private static PlayerDataManager playerDataManager;
    public static PlayerDataManager getDataManager(){return playerDataManager;}


}
