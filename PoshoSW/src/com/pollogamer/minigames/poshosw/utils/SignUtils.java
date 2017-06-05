package com.pollogamer.minigames.poshosw.utils;

import com.pollogamer.minigames.core.objects.Config;
import com.pollogamer.minigames.core.utils.LocationUtils;
import com.pollogamer.minigames.poshosw.config.ConfigManager;
import com.pollogamer.minigames.poshosw.sign.SkyWarsSign;
import org.bukkit.Location;

public class SignUtils {

    public static void createSignBuildUHC(String name, Location location){
        if(ConfigManager.signs.getConfigurationSection("signs.locations") != null){
            int id = ConfigManager.signs.getConfigurationSection("signs.locations").getKeys(false).size()+(1);
            boolean next = true;
            while (next){
                if(ConfigManager.signs.getString("signs.locations."+id) == null){
                    next = false;
                }else{
                    id++;
                }
            }
            ConfigManager.signs.set("signs.locations."+(id), LocationUtils.serializeLoc(location)+","+name);
            ConfigManager.signs.save();
        }else{
            ConfigManager.signs.set("signs.locations."+(1),LocationUtils.serializeLoc(location)+","+name);
        }
    }
    public static SkyWarsSign getSignArena(Location l){
        for(SkyWarsSign buildUHCSign : SkyWarsSign.signs){
            if(l.equals(buildUHCSign.getLocation())){
                return buildUHCSign;
            }
        }
        return null;
    }

    public static void deleteSignBuildUHC(Location location,String name){
        for(String s : ConfigManager.signs.getConfigurationSection("signs.locations").getKeys(false)){
            String locserialized = LocationUtils.serializeLoc(location);
            if(ConfigManager.signs.getString("signs.locations."+s).equals(locserialized+","+name)){
                ConfigManager.signs.set("signs.locations."+s,null);
                ConfigManager.signs.save();
            }
        }
    }

}
