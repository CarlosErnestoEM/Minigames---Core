package com.pollogamer.minigames.poshosw.task;

import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.core.manager.MessagesManager;
import com.pollogamer.minigames.core.utils.Countdown;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import org.bukkit.entity.Player;

public class LobbyTask {

    private SWArena arena;
    public int timee = 0;

    public LobbyTask(SWArena swArena){
        this.arena = swArena;
        start();
    }

    public void start(){
        if(arena.getArenaType().equals(ArenaType.SOLO)){
            arena.preGameTask = new PreGameTask(arena);
            return;
        }
        new Countdown<Principal>(Principal.getPlugin(),10){
            @Override
            public void running() {
                timee = time;
                if(arena.isStarting()){
                    if(arena.getPlayers().size() < arena.getMinPlayers() && !arena.isForcestart()){
                        //starting game cancelled
                        arena.setGameState(GameState.WAITING);
                        arena.setStarting(false);
                        cancel();
                    }
                    if(time == 10||time>0 && time<6){
                        arena.broadcast("&e¡El juego comienza en §c"+time+(time == 1 ? " §esegundo!" : " §esegundos!"));
                        //send title title(time) subtitle (&e¡Preparate para luchar!)
                        //action bar kit selected
                    }
                    if(time >0 && time<6){
                        for(Player player : arena.getPlayers()){
                            MessagesManager.sendTitle(player,"&c"+time,"&e¡Prepárate para luchar!",10,20,10);
                        }
                    }
                }else{
                    cancel();
                }
            }

            @Override
            public void end() {
                timee = time;
                arena.setStarted(true);
                arena.preGameTask = new PreGameTask(arena);
            }
        };
    }
}