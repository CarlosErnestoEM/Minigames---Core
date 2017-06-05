package com.pollogamer.minigames.core.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Countdown<PluginType extends JavaPlugin> extends BukkitRunnable {

    public int time  = 0;
    public PluginType plugin;

    public Countdown(PluginType pluginType,int time){
        this.time = time;
        runTaskTimer(pluginType,0,20);
    }

    @Override
    public void run() {
        if(time > 0){
            running();
            time--;
        }else{
            end();
            cancel();
        }
    }

    public abstract void running();

    public abstract void end();
}
