package com.pollogamer.minigames.poshosw.sign;

import com.pollogamer.minigames.core.arena.Arena;
import com.pollogamer.minigames.core.signs.MinigameSign;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsSign extends MinigameSign {

    public static List<SkyWarsSign> signs = new ArrayList<>();

    public SkyWarsSign(Location location, String name) {
        super(location, name);
        signs.add(this);
    }

    @Override
    public void update(){
        try {
            Sign sign = (Sign) getLocation().getBlock().getState();
            SWArena arena = Principal.getPlugin().getArenaManager().getArena(getName());
            for(int i = 0;i<4;i++){
                String line = ChatColor.translateAlternateColorCodes('&',ConfigManager.signs.getString("signs.line"+i).replace("%maxplayers%",arena.getMaxPlayers()+"").replace("%arena%",getName()).replace("%players%",arena.getPlayers().size()+"").replace("%state%",Principal.getPlugin().getArenaManager().getState(arena)));
                sign.setLine(i,line);
            }
            sign.update();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
