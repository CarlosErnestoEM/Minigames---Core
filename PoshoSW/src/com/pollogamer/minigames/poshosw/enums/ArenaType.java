package com.pollogamer.minigames.poshosw.enums;

public enum ArenaType {

    SOLO(1),
    TEAM(2),
    MEGA(5);

    int maxplayers = 0;

    ArenaType(int maxplayers){
        this.maxplayers = maxplayers;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
