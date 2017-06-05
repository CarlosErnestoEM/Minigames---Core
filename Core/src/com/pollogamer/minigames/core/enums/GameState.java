package com.pollogamer.minigames.core.enums;

public enum GameState {

    DISABLE("DISABLED"),
    WAITING("§aWaiting"),
    STARTING("§9Starting"),
    INGAME("§cIn Game");

    private String motd;

    GameState(String motd){
        this.motd = motd;
    }

    public String getMotd() {
        return motd;
    }
}
