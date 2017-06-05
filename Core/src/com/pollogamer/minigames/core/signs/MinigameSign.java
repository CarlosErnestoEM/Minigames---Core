package com.pollogamer.minigames.core.signs;

import org.bukkit.Location;

public abstract class MinigameSign{

    private Location location;
    private String name;

    public MinigameSign(Location location, String name) {
        this.location = location;
        this.name = name;
        update();
    }

    public abstract void update();


    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
