package com.pollogamer.minigames.core.utils;

import com.pollogamer.minigames.core.world.EmptyGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtils {

    public static void copyWorld(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {

        }
    }

    public static boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }


    public static void unloadWorld(World world) {
        if(world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    public static void loadWorld(String foldername){
        WorldCreator worldCreator = new WorldCreator(foldername);
        worldCreator.generator(new EmptyGenerator());
        Bukkit.getServer().createWorld(worldCreator);
    }

    public static void prepareWorld(World world){
        world.setAutoSave(false);
        world.setThundering(false);
        world.setStorm(false);
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnLocation(0, 100, 0);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
    }

}
