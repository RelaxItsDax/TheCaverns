package me.relaxitsdax.thecaverns;

import me.relaxitsdax.thecaverns.PlayerData.DataManager;
import me.relaxitsdax.thecaverns.PlayerData.JoinLeaveListener;
import me.relaxitsdax.thecaverns.PlayerData.PlayerData;
import me.relaxitsdax.thecaverns.Test.GetData;
import me.relaxitsdax.thecaverns.Test.SetData;
import me.relaxitsdax.thecaverns.Test.VariableCMD;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheCaverns extends JavaPlugin {


    public void onEnable() {

        INSTANCE = this;
        dataManager = new DataManager();



        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getCommand("loaddata").setExecutor(new VariableCMD());
        getCommand("check").setExecutor(new GetData());
        getCommand("setdata").setExecutor(new SetData());

        for (Player player : getServer().getOnlinePlayers()) {

            DataManager.add(player.getUniqueId(), new PlayerData(player.getUniqueId()));

        }







    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

    private static DataManager dataManager;
    public static DataManager getDataManager(){return dataManager;}


}
