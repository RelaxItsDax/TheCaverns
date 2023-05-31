package me.relaxitsdax.thecaverns;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class TheCaverns extends JavaPlugin {

    public void onEnable() {

        System.out.println("hiiii");

    }

    public void onDisable() {

    }

    private static TheCaverns INSTANCE;
    public static TheCaverns getInstance() {
        return INSTANCE;
    }

}
