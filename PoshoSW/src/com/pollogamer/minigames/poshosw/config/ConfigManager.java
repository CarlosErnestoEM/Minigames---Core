package com.pollogamer.minigames.poshosw.config;

import com.pollogamer.minigames.core.objects.Config;
import com.pollogamer.minigames.poshosw.Principal;

public class ConfigManager {

    public static Config<Principal> config;
    public static Config<Principal> signs;

    public ConfigManager(){
        config = new Config<>(Principal.getPlugin(),"config.yml");
        signs = new Config<>(Principal.getPlugin(),"signs.yml");
    }
}
