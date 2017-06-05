package com.pollogamer.minigames.poshosw.listener.bungee;

import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.bungeemode.Games;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerListener implements Listener{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        SWArena swArena = Games.getAvailableArena();
        if(swArena != null){

        }else{

        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event){
        String motd = "";
        for(SWArena swArena : Principal.getPlugin().getArenaManager().getArenas()){
            motd += swArena.getGameState().getMotd()+";";
        }
        event.setMotd(motd);
    }
}
