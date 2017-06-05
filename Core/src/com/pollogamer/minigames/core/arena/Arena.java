package com.pollogamer.minigames.core.arena;

import com.pollogamer.minigames.core.enums.GameState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private List<Player> players;
    private List<Player> spectators;
    private int minPlayers;
    private int maxPlayers;
    private String name;
    private GameState gameState;
    private Location lobby;
    private boolean starting;
    private boolean started;
    private boolean forcestart;

    public Arena(int minPlayers, int maxPlayers, String name) {
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.name = name;
        players = new ArrayList<>();
        spectators = new ArrayList<>();
        gameState = GameState.DISABLE;
    }

    public void broadcast(String message){
        for(Player player : players){
            if(player.isOnline()){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
            }
        }
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public List<Player> getSpectators() {
        return spectators;
    }

    public boolean isForcestart() {
        return forcestart;
    }

    public void setForcestart(boolean forcestart) {
        this.forcestart = forcestart;
    }
}
