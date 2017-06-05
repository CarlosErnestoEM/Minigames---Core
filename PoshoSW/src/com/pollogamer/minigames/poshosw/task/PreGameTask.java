package com.pollogamer.minigames.poshosw.task;

import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.core.manager.MessagesManager;
import com.pollogamer.minigames.core.utils.Countdown;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.GameEvents;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.cages.CageManager;
import com.pollogamer.minigames.poshosw.enums.ArenaMode;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import com.pollogamer.minigames.poshosw.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PreGameTask {

    private SWArena arena;
    public int timee = 0;

    public PreGameTask(SWArena swArena){
        this.arena = swArena;
        start();
    }

    public void start(){
        new Countdown<Principal>(Principal.getPlugin(),10){
            @Override
            public void running() {
                if(arena.getArenaType().equals(ArenaType.SOLO)){
                    arena.broadcast("&eEl juego comienza en &c"+time+(time == 1 ? " &esegundo!" : " &esegundos!"));
                }else{
                    arena.broadcast("&eLas jaulas se abrirán en &c"+time+(time == 1 ? " &esegundo!" : " &esegundos!"));
                }
                if(arena.getArenaType().equals(ArenaType.SOLO)){
                    if(time >0 && time<6){
                        for(Player player : arena.getPlayers()){
                            MessagesManager.sendTitle(player,"&c"+time,"&e¡Prepárate para luchar!",10,20,10);
                        }
                    }
                }
                timee = time;
            }

            @Override
            public void end() {
                if(arena.getArenaType().equals(ArenaType.SOLO)){
                    arena.broadcast("&c&l¡Hacer equipos no está permitido en modo Solo!");
                }else{
                    arena.broadcast("&c&l¡Cross Teamming / Hacer equipo con otros equipos no está permitido!");
                }
                CageManager.removeAllCages(arena);
                arena.gameEvents = new GameEvents();
                arena.setGameState(GameState.INGAME);
                arena.setStarted(true);
                arena.broadcast("&e¡Las jaulas se han abierto! &c¡A LUCHAR!");
                for(Player player : arena.getPlayers()){
                    PlayerUtils.setGameScoreboard(player,arena);
                    MessagesManager.sendCenteredMessage(player,"&a▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
                    MessagesManager.sendCenteredMessage(player,"&LSkyWars");
                    MessagesManager.sendCenteredMessage(player,"");
                    MessagesManager.sendCenteredMessage(player,"&e&lRecolecta recursos y equipamiento en tu isla");
                    MessagesManager.sendCenteredMessage(player,"&e&lpara eliminar a los otros jugadores");
                    MessagesManager.sendCenteredMessage(player,"&e&l¡Ve a la isla del medio y recoge los cofres");
                    MessagesManager.sendCenteredMessage(player,"&e&lespeciales!");
                    MessagesManager.sendCenteredMessage(player,"&e&lcon items especiales!");
                    MessagesManager.sendCenteredMessage(player,"");
                    MessagesManager.sendCenteredMessage(player,"&a▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
                }
                timee = time;
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        arena.setFallDamage(true);
                    }
                }.runTaskLater(Principal.getPlugin(),60);
            }
        };
    }


}
