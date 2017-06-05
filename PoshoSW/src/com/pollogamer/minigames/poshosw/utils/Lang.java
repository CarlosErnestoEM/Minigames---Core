package com.pollogamer.minigames.poshosw.utils;

import com.pollogamer.minigames.core.utils.LocationUtils;
import com.pollogamer.minigames.poshosw.config.ConfigManager;
import org.bukkit.Location;

public class Lang {

    public static boolean BungeeMode = false;
    public static Location lobby;

    public Lang(){
        start();
    }

    public static void start(){
        try {
            lobby = LocationUtils.deserializeLoc(ConfigManager.config.getString("locations.lobby"));
        }catch (Exception e){}
    }
}
