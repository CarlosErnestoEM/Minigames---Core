package com.pollogamer.minigames.poshosw.utils;

import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.core.manager.MessagesManager;
import com.pollogamer.minigames.core.objects.Config;
import com.pollogamer.minigames.core.utils.Countdown;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.arena.SkyWarsFile;
import com.pollogamer.minigames.poshosw.cages.CageManager;
import com.pollogamer.minigames.poshosw.enums.ArenaMode;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import com.pollogamer.minigames.poshosw.task.LobbyTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class ArenaUtils {

    public static void addPlayer(Player player, String arena) {
        SWArena swArena = Principal.getPlugin().getArenaManager().addPlayer(player, arena, "&e¡&7%player% &ese ha unido (&b%players%&e/&b%maxplayers%&e)!");
        if (swArena != null) {
            if (swArena.getPlayers().contains(player)) {
                if (!swArena.isStarted()) {
                    if (swArena.getArenaType().equals(ArenaType.SOLO)) {
                        Location location = swArena.getNextLocation(player);
                        CageManager.createCage(ArenaType.SOLO, location);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.teleport(location.add(0, 1, 0));
                                //set clean player
                            }
                        }.runTaskLater(Principal.getPlugin(), 1);
                    } else if (swArena.getArenaType().equals(ArenaType.TEAM) || swArena.getArenaType().equals(ArenaType.MEGA)) {
                        player.teleport(swArena.getLobby());
                    }
                    if (!swArena.isStarting()) {
                        if (swArena.getPlayers().size() >= swArena.getMinPlayers()) {
                            swArena.setStarting(true);
                            swArena.setGameState(GameState.STARTING);
                            swArena.lobbyTask = new LobbyTask(swArena);
                        }
                    }
                    MessagesManager.sendTitle(player, "&eSkyWars", (swArena.getMode().equals(ArenaMode.NORMAL) ? "&aModo Normal" : "&cModo Insane"), 10, 20, 10);
                    PlayerUtils.setLobbyScoreboard(player, swArena);
                } else {
                    //is spectator
                }
            }
        }
    }

    public static void removePlayer(Player player){
        SWArena swArena = Principal.getPlugin().getArenaManager().removePlayer(player,"&e¡&7%player% &ese ha salido!");
        if(swArena != null){
            if(swArena.getGameState().equals(GameState.STARTING)||swArena.getGameState().equals(GameState.WAITING)){
                swArena.removeCage(player);
                player.teleport(Lang.lobby);
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }

    public static void loadArenas(){
        Principal.getPlugin().getArenaManager().loadArenas();
        for(File f : Principal.getPlugin().getArenaManager().getArenasfiles()){
            SkyWarsFile skyWarsFile = new SkyWarsFile(new Config<Principal>(Principal.getPlugin(),f));
            new SWArena(skyWarsFile);
            Principal.getPlugin().getLogger().info("Arena "+skyWarsFile.getArenaid()+" ha sido cargada!");
        }
    }
}
