package com.pollogamer.minigames.poshosw;

import com.pollogamer.minigames.core.CorePlugin;
import com.pollogamer.minigames.core.arena.ArenaManager;
import com.pollogamer.minigames.core.signs.MinigameSign;
import com.pollogamer.minigames.core.signs.SignManager;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.commands.CMDSw;
import com.pollogamer.minigames.poshosw.commands.setup.CMDSws;
import com.pollogamer.minigames.poshosw.config.ConfigManager;
import com.pollogamer.minigames.poshosw.listener.ArenaListener;
import com.pollogamer.minigames.poshosw.utils.ArenaUtils;
import com.pollogamer.minigames.poshosw.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;


public class Principal extends CorePlugin {

    private static Principal plugin;

    private ArenaManager<Principal,SWArena> arenaManager;
    private SignManager<MinigameSign> signManager;

    public void onEnable(){
        init();
    }

    public void init(){
        plugin = this;
        getLogger().info("Activando PoshoSkyWars version "+getDescription().getVersion());
        getLogger().info("Modo "+(Lang.BungeeMode ? "BungeeMode" : "Multiarena"));
        new ConfigManager();
        new Lang();
        registerCommands();
        arenaManager = new ArenaManager<>(this);
        ArenaUtils.loadArenas();
        registerListener(new ArenaListener());
        if(!Lang.BungeeMode){
            signManager = new SignManager<>(ConfigManager.signs,arenaManager);
            return;
        }
    }

    public void registerCommands(){
        getCommand("sw").setExecutor(new CMDSw());
        getCommand("sws").setExecutor(new CMDSws());
    }

    public void registerListener(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener,this);
    }

    public static Principal getPlugin(){return plugin;}

    public ArenaManager<Principal,SWArena> getArenaManager(){return arenaManager;}

}

