package com.pollogamer.minigames.poshosw.arena;

import com.pollogamer.minigames.core.objects.Config;
import com.pollogamer.minigames.core.utils.LocationUtils;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.enums.ArenaMode;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import com.pollogamer.minigames.poshosw.enums.League;
import org.bukkit.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkyWarsFile {

    public Config config;

    public SkyWarsFile(Config config){
        this.config = config;
        try {
            arenaMode = ArenaMode.valueOf(config.getString("arenamode"));
        }catch (Exception e){
            Principal.getPlugin().getLogger().info("Invalid ArenaMode, Please usage NORMAL or INSANE");
        }

        try {
            arenaType = ArenaType.valueOf(config.getString("gametype"));
        }catch (Exception e){
            Principal.getPlugin().getLogger().info("Invalid GameType please usage SOLO or TEAM");
        }

        try {
            league = League.valueOf(config.getString("league"));
        }catch (Exception e){
            Principal.getPlugin().getLogger().info("Invalid League Please usage UNRANKED or RANKED");
        }

        arenaid = config.getString("arenaid");
        mapname = config.getString("mapname");
        minplayers = config.getInt("minplayers");

        try {
            lobby = LocationUtils.deserializeLoc("locations.lobby");
            lobbyp1 = LocationUtils.deserializeLoc("locations.lobbypos2");
            lobbyp2 = LocationUtils.deserializeLoc("locations.lobbypos2");
        }catch (Exception e){}

        for(String s : config.getStringList("locations.spawns")){
            Location location = LocationUtils.deserializeLoc(s);
            spawns.add(location);
        }

        maxplayers = getSpawns().size();
    }

    public SkyWarsFile(String filename){
        config = new Config(Principal.getPlugin(),new File(Principal.getPlugin().getDataFolder()+"/arenas",filename),false);
    }

    private ArenaMode arenaMode;
    private ArenaType arenaType;
    private League league;
    private String arenaid;
    private String mapname;
    private int minplayers;
    private int maxplayers;

    private Location lobby;
    private Location lobbyp1;
    private Location lobbyp2;
    private List<Location> spawns = new ArrayList<>();

    public ArenaMode getArenaMode() {
        return arenaMode;
    }

    public void addSpawn(Location location){
        List<String> list = config.getStringList("locations.spawns");
        list.add(LocationUtils.serializeLoc(location));
        config.set("locations.spawns",list);
        config.save();
    }

    public ArenaType getArenaType() {
        return arenaType;
    }

    public League getLeague() {
        return league;
    }

    public String getArenaid() {
        return arenaid;
    }

    public String getMapname() {
        return mapname;
    }

    public int getMinplayers() {
        return minplayers;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public Location getLobby() {
        return lobby;
    }

    public Location getLobbyp1() {
        return lobbyp1;
    }

    public Location getLobbyp2() {
        return lobbyp2;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void setArenaMode(ArenaMode arenaMode) {
        this.arenaMode = arenaMode;
    }

    public void setArenaType(ArenaType arenaType) {
        this.arenaType = arenaType;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void setArenaid(String arenaid) {
        this.arenaid = arenaid;
    }

    public void setMapname(String mapname) {
        this.mapname = mapname;
    }

    public void setMinplayers(int minplayers) {
        this.minplayers = minplayers;
    }

    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public void setLobbyp1(Location lobbyp1) {
        this.lobbyp1 = lobbyp1;
    }

    public void setLobbyp2(Location lobbyp2) {
        this.lobbyp2 = lobbyp2;
    }


    public void saveToConfig(){
        config.set("arenamode",getArenaMode());
        config.set("gametype",getArenaType().toString());
        config.set("league",getLeague());
        config.set("arenaid",getArenaid());
        config.set("mapname",getMapname());
        config.set("minplayers",getMinplayers());
        config.set("locations.lobby",getLobby());
        config.set("locations.lobbypos1",getLobbyp1());
        config.set("locations.lobbypos2",getLobbyp2());
        config.save();
    }
}
