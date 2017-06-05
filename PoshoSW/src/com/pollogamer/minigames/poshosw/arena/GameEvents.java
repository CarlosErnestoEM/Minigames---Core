package com.pollogamer.minigames.poshosw.arena;

import com.pollogamer.minigames.core.utils.TimeUtils;
import com.pollogamer.minigames.poshosw.Principal;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEvents extends BukkitRunnable{

    public String eventname;
    public int time = 0;
    public boolean refill_1 = false;

    public GameEvents(){
        time = 210;
        eventname = "§aRellenado "+ TimeUtils.serializetime(time);
        runTaskTimer(Principal.getPlugin(),0,20);
    }

    @Override
    public void run() {
        if(!refill_1){
         if(time == 0){
          refill_1 = true;
         }else{
             eventname = "§aRellenado "+ TimeUtils.serializetime(time);
         }
        }
        time--;
    }
}
