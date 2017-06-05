package com.pollogamer.minigames.core.arena;


import com.pollogamer.minigames.core.enums.GameState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArenaManager<PluginType extends JavaPlugin,ArenaType extends Arena> {

    private List<ArenaType> arenas;
    private ArenaType arenaType;
    private PluginType pluginType;
    private File[] arenasfiles;

    public ArenaManager(PluginType pluginType){
        this.pluginType = pluginType;
        arenas = new ArrayList<>();
    }

    public List<ArenaType> getArenas(){return arenas;}

    public void loadArenas(){
        File folder = new File(pluginType.getDataFolder()+"/arenas");
        File folder2 = new File(pluginType.getDataFolder()+"/worlds");
        if(!folder.exists()){
            folder.mkdir();
        }
        if(!folder2.exists()){
            folder2.mkdir();
        }
        File arenafolder = new File(pluginType.getDataFolder()+"/arenas");
        arenasfiles = arenafolder.listFiles();
    }

    public ArenaType addPlayer(Player player, String arena,String message){
        ArenaType arenaType = getArena(arena);
        if(arenaType != null){
            ArenaType arenaType1 = getArena(player);
            if(arenaType1 == null){
                if(!arenaType.isStarted()){
                    if(arenaType.getPlayers().size() < arenaType.getMaxPlayers()){
                        arenaType.getPlayers().add(player);
                        arenaType.broadcast(message.replace("%maxplayers%",arenaType.getMaxPlayers()+"").replace("%players%",arenaType.getPlayers().size()+"").replace("%player%", player.getName()));
                    }else{
                        player.sendMessage("§E§LSkyWars §7» §aArena llena");
                    }
                }else{
                    arenaType.getSpectators().add(player);
                    //set gm 3
                }
            }else{
                player.sendMessage("§E§LSkyWars §7» §aYa estas dentro de 1 partida!");
            }
        }
        return arenaType;
    }

    public String getState(ArenaType a){
        if(a.getGameState().equals(GameState.WAITING)){
            return "&aEsperando";
        }else if(a.getGameState().equals(GameState.STARTING)){
            return "&bEmpezando";
        }else if(a.getGameState().equals(GameState.INGAME)){
            return "&cEn juego";
        }else if(a.getGameState().equals(GameState.DISABLE)){
            return "Restarting";
        }else{
            return null;
        }
    }

    public ArenaType removePlayer(Player player,String message){
        ArenaType arenaType = getArena(player);
        if(arenaType != null){
            if(!arenaType.isStarted()){
                    arenaType.getPlayers().remove(player);
                    arenaType.broadcast(message.replace("%maxplayers%",arenaType.getMaxPlayers()+"").replace("%players%",arenaType.getPlayers().size()+"").replace("%player%", player.getName()));
            }else{
                arenaType.getPlayers().remove(player);
                arenaType.getSpectators().add(player);
                //set gm 3
            }
        }
        return arenaType;
    }


    public ArenaType getArena(String name){
        for(ArenaType arenas : arenas){
            if(arenas.getName().equalsIgnoreCase(name)){
                return arenas;
            }
        }
        return null;
    }

    public ArenaType getArena(Player player){
        for(ArenaType arenas : arenas){
            if(arenas.getPlayers().contains(player)){
                return arenas;
            }
        }
        return null;
    }

    public File[] getArenasfiles() {
        return arenasfiles;
    }
}
