package com.pollogamer.minigames.poshosw.bungeemode;

import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;

public class Games {

    public static SWArena getAvailableArena(){
        for(SWArena arenaType : Principal.getPlugin().getArenaManager().getArenas()){
            if(arenaType.getGameState().equals(GameState.WAITING)||arenaType.getGameState().equals(GameState.STARTING) && arenaType.getMaxPlayers() < arenaType.getPlayers().size()){
                return arenaType;
            }
        }
        return null;
    }
}
