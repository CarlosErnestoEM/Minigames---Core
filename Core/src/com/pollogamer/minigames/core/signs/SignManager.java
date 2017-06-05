package com.pollogamer.minigames.core.signs;


import com.pollogamer.minigames.core.arena.Arena;
import com.pollogamer.minigames.core.arena.ArenaManager;
import com.pollogamer.minigames.core.objects.Config;
import com.pollogamer.minigames.core.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class SignManager<Sign extends MinigameSign> {

    private ArenaManager arenaManager;
    private Config config;
    public List<Sign> signs = new ArrayList<>();

    public SignManager(Config config,ArenaManager arenaManager){
        this.config = config;
        this.arenaManager = arenaManager;
        loadSigns();
        update();
    }

    public void loadSigns(){
        if(config.getConfigurationSection("signs.locations") != null){
            for(String s : config.getConfigurationSection("signs.locations").getKeys(false)){
                Location l = LocationUtils.deserializeLoc(config.getString("signs.locations."+s));
                String name = LocationUtils.desearializeName(config.getString("signs.locations."+s));
                new MinigameSign(l,name);
            }
        }
    }

    public void update(){
        for(Sign signn : signs){
            try {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) signn.getLocation().getBlock().getState();
                Arena arena = arenaManager.getArena(signn.getName());
                for(int i = 0;i<4;i++){
                    String line = ChatColor.translateAlternateColorCodes('&',config.getString("signs.line"+i).replace("%maxplayers%",arena.getMaxPlayers()+"").replace("%arena%",signn.getName()).replace("%players%",arena.getPlayers().size()+"").replace("%state%",arenaManager.getState(arena)));
                    sign.setLine(i,line);
                }
                sign.update();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
