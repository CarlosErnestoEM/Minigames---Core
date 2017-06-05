package com.pollogamer.minigames.core.utils;

public class TimeUtils {

    public static String serializetime(int time) {
        return String.format("%02d:%02d", time/60, time%60);
    }
}
