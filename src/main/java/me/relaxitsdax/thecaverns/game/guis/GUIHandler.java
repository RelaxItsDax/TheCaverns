package me.relaxitsdax.thecaverns.game.guis;

import me.relaxitsdax.thecaverns.game.guis.types.GUI;

import java.util.*;

public class GUIHandler {
    private GUI current;

    public GUIHandler(UUID uuid) {
        this.current = null;
        GUIHandlerManager.add(uuid, this);
    }

    public GUI getCurrentGUI() {
        return current;
    }

    public void setCurrent(GUI current) {
        this.current = current;
    }

    public void removeCurrent() {
        this.current = null;
    }

}
