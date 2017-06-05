package com.pollogamer.minigames.poshosw.task;

import com.pollogamer.minigames.core.utils.Countdown;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WinnerTask extends BukkitRunnable {

    private SWArena swArena;
    private Player player;

    public WinnerTask(SWArena swArena){
            this.swArena = swArena;
        try {
            player = swArena.getPlayers().get(0);
        }catch (Exception e){}
        runTaskLater(Principal.getPlugin(),20);
    }

    @Override
    public void run() {
        new Countdown<Principal>(Principal.getPlugin(),10){
            @Override
            public void running() {
                if(time == 9){
                    broadcast();
                }
            }

            @Override
            public void end() {

            }
        };
    }

    public void broadcast(){
        if(swArena.getArenaType().equals(ArenaType.SOLO)){
            swArena.broadcast("");
            if(player != null){
                swArena.broadcast("El ganador es "+player.getName());
            }
        }
    }
}
