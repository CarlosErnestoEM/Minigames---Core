package com.pollogamer.minigames.core.objects;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config<PluginType extends JavaPlugin> extends YamlConfiguration {

    private PluginType plugin;
    private File configFile;
    private String filename;

    public Config(PluginType pluginType,String file) {
        this.plugin = pluginType;
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        this.configFile = new File(this.plugin.getDataFolder(), file);
        this.filename = file;
        saveDefault();
    }

    public Config(PluginType pluginType,File file) {
        this.plugin = pluginType;
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        this.configFile = file;
        reload();
    }

    public Config(PluginType pluginType,File file,boolean b) {
        this.plugin = pluginType;
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        this.configFile = file;
        if(!configFile.exists()){
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reload();
    }
    //REEMPLAZAR GETSTRING, GETLISTCOLORIZED

    public void reload(){
        try{
            super.load(this.configFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            super.save(this.configFile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveDefault(){
        if(!configFile.exists()) {
            plugin.saveResource(filename, false);
            plugin.getLogger().info("Archivo "+filename+" creado!");
            reload();
        }else{
            reload();
        }
    }

}
