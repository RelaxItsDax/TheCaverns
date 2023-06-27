package me.relaxitsdax.thecaverns.game.guis;

import me.relaxitsdax.thecaverns.game.enums.guis.GUIs;
import me.relaxitsdax.thecaverns.game.guis.types.GUI;

import java.util.*;

public class GUIHandler {
    private GUI current;
    private final Map<GUIs, GUI> data = new HashMap<>();

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

    public GUI getGUIData(GUIs gui) {
        return data.get(gui);
    }

    public void setGUI(GUIs guiType, GUI data) {
        this.data.put(guiType, data);
    }

}
