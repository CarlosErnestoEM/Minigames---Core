package com.pollogamer.minigames.core.utils;

import org.bukkit.event.player.PlayerMoveEvent;

public class Utils {

    public static boolean moved(PlayerMoveEvent event) {
        return !(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ());
    }
}
