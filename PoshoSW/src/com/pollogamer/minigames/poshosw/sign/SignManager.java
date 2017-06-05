package com.pollogamer.minigames.poshosw.sign;

import com.pollogamer.minigames.core.utils.LocationUtils;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.config.ConfigManager;
import com.pollogamer.minigames.poshosw.listener.SignListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class SignManager extends BukkitRunnable{

    public SignManager(){
        if(ConfigManager.signs.getConfigurationSection("signs.locations") != null){
            for(String s : ConfigManager.signs.getConfigurationSection("signs.locations").getKeys(false)){
                Location l = LocationUtils.deserializeLoc(ConfigManager.signs.getString("signs.locations."+s));
                String name = LocationUtils.desearializeName(ConfigManager.signs.getString("signs.locations."+s));
                new SkyWarsSign(l,name);
            }
        }
        Bukkit.getServer().getPluginManager().registerEvents(new SignListener(),Principal.getPlugin());
        runTaskTimer(Principal.getPlugin(),2,4);
    }


    @Override
    public void run() {
        for(SkyWarsSign skyWarsSign : SkyWarsSign.signs){
            skyWarsSign.update();
        }
    }
}
