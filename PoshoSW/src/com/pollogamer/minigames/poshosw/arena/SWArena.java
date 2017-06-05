package com.pollogamer.minigames.poshosw.arena;

import com.pollogamer.minigames.core.arena.Arena;
import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.core.utils.FileUtils;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.cages.CageManager;
import com.pollogamer.minigames.poshosw.enums.ArenaMode;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import com.pollogamer.minigames.poshosw.enums.League;
import com.pollogamer.minigames.poshosw.task.LobbyTask;
import com.pollogamer.minigames.poshosw.task.PreGameTask;
import com.pollogamer.minigames.poshosw.task.WinnerTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SWArena extends Arena{

    private ArenaType arenaType;
    private League league;
    private ArenaMode mode;
    private List<Location> spawns = new ArrayList<>();
    private Map<Player,Location> locationuseds = new HashMap<>();
    public LobbyTask lobbyTask;
    public PreGameTask preGameTask;
    public WinnerTask winnerTask;
    public GameEvents gameEvents;
    private String arenaid;
    private String mapname;
    private boolean fallDamage = false;
    private Map<Player,Integer> kills = new HashMap<>();

    public SWArena(String arenaid,int minPlayers, int maxPlayers, String mapname,HashMap<Integer,Location> spawns) {
        super(minPlayers, maxPlayers, arenaid);
        this.arenaid = arenaid;
        this.mapname = mapname;
        try {
            arenaType = ArenaType.valueOf("type");
            league = League.valueOf("league");
            mode = ArenaMode.valueOf("mode");
            setGameState(GameState.WAITING);
        }catch (Exception e){
            Principal.getPlugin().getLogger().info("Invalid ArenaType (Please usage SOLO, TEAM, MEGA) or League (RANKED, UNRANKED)");
        }
        Principal.getPlugin().getArenaManager().getArenas().add(this);
    }

    public SWArena(SkyWarsFile skyWarsFile){
        super(skyWarsFile.getMinplayers(),skyWarsFile.getMaxplayers(),skyWarsFile.getArenaid());
        this.arenaid = skyWarsFile.getArenaid();
        this.mapname = skyWarsFile.getMapname();
        this.arenaType = skyWarsFile.getArenaType();
        this.league = skyWarsFile.getLeague();
        this.mode = skyWarsFile.getArenaMode();
        this.spawns = skyWarsFile.getSpawns();
        setGameState(GameState.WAITING);
        Principal.getPlugin().getArenaManager().getArenas().add(this);
    }

    public Location getNextLocation(Player player){
        Location location = null;
        for(Location location1 : spawns){
            if(!locationuseds.containsValue(location1)){
                locationuseds.put(player,location1);
                location = location1;
                break;
            }
        }
        return location;
    }

    public void removeCage(Player player){
        if(locationuseds.containsKey(player)){
            Location loc = locationuseds.get(player);
            CageManager.removeCage(loc);
            locationuseds.remove(player);
        }
    }

    public void checkWin(){
        if(arenaType.equals(ArenaType.SOLO)){
            if(getPlayers().size() == 1){
                winnerTask = new WinnerTask(this);
            }
        }
    }

    public void addKill(Player player){
        if(kills.containsKey(player)){
            kills.put(player,(kills.get(player)+1));
        }else{
            kills.put(player,1);
        }
    }

    public int getKills(Player player){
        try {
            return kills.get(player);
        }catch (Exception e){
            return 0;
        }
    }


    public void endArena(){
        World world = Bukkit.getWorld("");
        new BukkitRunnable(){
            @Override
            public void run() {
                world.save();
                FileUtils.copyWorld(world.getWorldFolder(), new File(Principal.getPlugin().getDataFolder()+"/worlds/"+world.getName()));
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        for(Player player1 : world.getPlayers()){
                            player1.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                        }
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                FileUtils.unloadWorld(world);
                                FileUtils.deleteWorld(world.getWorldFolder());
                            }
                        }.runTaskAsynchronously(Principal.getPlugin());
                    }
                }.runTask(Principal.getPlugin());
            }
        }.runTaskAsynchronously(Principal.getPlugin());
        fallDamage = false;
        kills = new HashMap<>();
        locationuseds = new HashMap<>();
        setGameState(GameState.WAITING);
    }


    public ArenaType getArenaType() {
        return arenaType;
    }

    public League getLeague() {
        return league;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public ArenaMode getMode() {
        return mode;
    }

    public LobbyTask getLobbyTask() {
        return lobbyTask;
    }

    public PreGameTask getPreGameTask() {
        return preGameTask;
    }

    public String getArenaid() {
        return arenaid;
    }

    public String getMapname() {
        return mapname;
    }

    public boolean isFallDamage() {
        return fallDamage;
    }

    public void setFallDamage(boolean fallDamage) {
        this.fallDamage = fallDamage;
    }


}
