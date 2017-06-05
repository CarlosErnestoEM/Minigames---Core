package com.pollogamer.minigames.poshosw.utils;

import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.core.utils.Scoreboard;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.enums.ArenaMode;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;

public class PlayerUtils {


    public static Calendar cal = Calendar.getInstance();
    public static int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    public static int year = cal.get(Calendar.YEAR);
    public static int month = cal.get(Calendar.MONTH);
    public static String time = "0"+(month+1)+"/"+dayOfMonth+"/"+(year);

    public static void setLobbyScoreboard(Player player, SWArena swArena){
        //Scoreboard title are SKYWARS WITH &E&L AND &F&L 3 SECONDS
        Scoreboard scoreboard = new Scoreboard("&e&lSKYWARS");
        scoreboard.add(" ",8);
        scoreboard.add("Jugadores: §a"+swArena.getPlayers().size()+"/"+swArena.getMaxPlayers(),7);
        scoreboard.add("  ",6);
        if(swArena.getArenaType().equals(ArenaType.SOLO)){
            scoreboard.add((swArena.isStarting() ? "Comenzando en &a"+swArena.getPreGameTask().timee+"s" : "Esperando..."),5);
        }else{
            scoreboard.add((swArena.isStarting() ? "Comenzando en &a"+swArena.getLobbyTask().timee+"s" : "Esperando..."),5);
        }
        scoreboard.add("   ",4);
        scoreboard.add("Servidor:&a "+swArena.getArenaid(),3);
        scoreboard.add("    ",2);
        scoreboard.add("&ewwww.hypixel.net",1);
        scoreboard.update();
        scoreboard.send(player);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(player.isOnline()){
                    SWArena swArena1 = Principal.getPlugin().getArenaManager().getArena(player);
                    if(swArena1 != swArena){
                        cancel();
                        return;
                    }
                    if(!swArena.isStarted()){
                        scoreboard.add("Jugadores: §a"+swArena.getPlayers().size()+"/"+swArena.getMaxPlayers(),7);
                        if(swArena.getArenaType().equals(ArenaType.SOLO)){
                            scoreboard.add((swArena.isStarting() ? "Comenzando en &a"+swArena.getPreGameTask().timee+"s" : "Esperando..."),5);
                        }else{
                            scoreboard.add((swArena.isStarting() ? "Comenzando en &a"+swArena.getLobbyTask().timee+"s" : "Esperando..."),5);
                        }
                        scoreboard.update();
                        scoreboard.send(player);
                    }else{
                        cancel();
                    }
                }else{
                    cancel();
                }
            }
        }.runTaskTimer(Principal.getPlugin(),10,10);
    }

    public static void setGameScoreboard(Player player, SWArena swArena){
        if(swArena.getArenaType().equals(ArenaType.SOLO)){
            Scoreboard scoreboard = new Scoreboard("&e&lSKYWARS");
            scoreboard.add("&7Solo "+time,13);
            scoreboard.add(" ",12);
            scoreboard.add("Siguiente Evento:",11);
            scoreboard.add(swArena.gameEvents.eventname,10);
            scoreboard.add("  ",9);
            scoreboard.add("Jugadores restantes:&a "+swArena.getPlayers().size(),8);
            scoreboard.add("   ",7);
            scoreboard.add("Kills: &a"+swArena.getKills(player),6);
            scoreboard.add("    ",5);
            scoreboard.add("Mapa: &a"+swArena.getMapname(),4);
            scoreboard.add("Modo:&a "+(swArena.getMode().equals(ArenaMode.NORMAL) ? "Normal" : "&cInsane"),3);
            scoreboard.add("     ",2);
            scoreboard.add("&ewww.hypixel.net",1);
            scoreboard.update();
            scoreboard.send(player);
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(player.isOnline()){
                        if(swArena.getGameState().equals(GameState.INGAME)){
                            scoreboard.add(swArena.gameEvents.eventname,10);
                            scoreboard.add("Jugadores restantes:&a "+swArena.getPlayers().size(),8);
                            scoreboard.add("Kills: &a0",6);
                            scoreboard.update();
                            scoreboard.send(player);
                        }else{
                            cancel();
                        }
                    }else{
                        cancel();
                    }
                }
            }.runTaskTimer(Principal.getPlugin(),10,10);
        }
    }
}
