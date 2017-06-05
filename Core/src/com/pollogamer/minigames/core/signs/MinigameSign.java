package com.pollogamer.minigames.core.signs;

import org.apache.logging.log4j.core.config.plugins.PluginType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class MinigameSign{

    public static List<MinigameSign> signs = new ArrayList<>();
    private Location location;
    private String name;

    public MinigameSign(Location location, String name) {
        this.location = location;
        this.name = name;
        signs.add(this);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
