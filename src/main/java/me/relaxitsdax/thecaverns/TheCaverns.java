package me.relaxitsdax.thecaverns;

import me.relaxitsdax.thecaverns.Test.Test;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheCaverns extends JavaPlugin {

    public void onEnable() {

        INSTANCE = this;

        System.out.println("hiiii");
        getServer().getPluginManager().registerEvents(new Test(), this);

    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

}
