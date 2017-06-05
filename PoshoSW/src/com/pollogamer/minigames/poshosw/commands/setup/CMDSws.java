package com.pollogamer.minigames.poshosw.commands.setup;

import com.pollogamer.minigames.core.manager.MessagesManager;
import com.pollogamer.minigames.core.utils.FileUtils;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SkyWarsFile;
import com.pollogamer.minigames.poshosw.enums.ArenaMode;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class CMDSws implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("The command only can be executed by the player");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("poshoskywars.command.setup")){
            player.sendMessage("§cYou don't have permissions to execute this command");
            return true;
        }
        if(args.length == 0){
         sendHelpPage(player);
         return true;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("addspawn")){
                SkyWarsFile skyWarsFile = new SkyWarsFile(player.getWorld().getName()+".yml");
                skyWarsFile.addSpawn(player.getLocation());
            }else if(args[0].equalsIgnoreCase("savemap")){
                World world = player.getWorld();
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        world.save();
                        FileUtils.copyWorld(world.getWorldFolder(), new File(Principal.getPlugin().getDataFolder()+"/worlds/"+world.getName()));
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                for(Player player1 : world.getPlayers()){
                                    player1.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                                }
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        FileUtils.unloadWorld(world);
                                        FileUtils.deleteWorld(world.getWorldFolder());
                                    }
                                }.runTaskAsynchronously(Principal.getPlugin());
                            }
                        }.runTask(Principal.getPlugin());
                    }
                }.runTaskAsynchronously(Principal.getPlugin());
            }else{
                sendHelpPage(player);
            }
        }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("loadworld")){
                        FileUtils.loadWorld(args[1]);
                        player.sendMessage("World loaded");
                        player.setGameMode(GameMode.CREATIVE);
                        player.setFlying(true);
                        player.teleport(new Location(Bukkit.getWorld(args[1]),0,100,0));
                }else if(args[0].equalsIgnoreCase("setminplayers")){
                    Integer i = null;
                    try {
                        i = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        player.sendMessage("Please usage a number!");
                    }finally {
                        if(i != null){
                            SkyWarsFile skyWarsFile = new SkyWarsFile(player.getWorld().getName()+".yml");
                            skyWarsFile.setMinplayers(i);
                            skyWarsFile.saveToConfig();
                        }
                    }
                }else if(args[0].equalsIgnoreCase("setmapid")){
                    SkyWarsFile skyWarsFile = new SkyWarsFile(player.getWorld().getName()+".yml");
                    skyWarsFile.setArenaid(args[1]);
                    skyWarsFile.saveToConfig();
                }else if(args[0].equalsIgnoreCase("setmapname")){
                    SkyWarsFile skyWarsFile = new SkyWarsFile(player.getWorld().getName()+".yml");
                    skyWarsFile.setMapname(args[1]);
                    skyWarsFile.saveToConfig();
                }else if(args[0].equalsIgnoreCase("setmode")){
                    SkyWarsFile skyWarsFile = new SkyWarsFile(player.getWorld().getName()+".yml");
                    skyWarsFile.setArenaMode(ArenaMode.valueOf(args[1]));
                    skyWarsFile.saveToConfig();
                }else if(args[0].equalsIgnoreCase("settype")){
                    SkyWarsFile skyWarsFile = new SkyWarsFile(player.getWorld().getName()+".yml");
                    skyWarsFile.setArenaType(ArenaType.valueOf(args[1]));
                    skyWarsFile.saveToConfig();
                }
        }
        return true;
    }

    public void sendHelpPage(Player player){
        MessagesManager.sendCenteredMessage(player,"&a&lPosho SkyWars");
        MessagesManager.sendCenteredMessage(player, "&cSetup");
        MessagesManager.sendCenteredMessage(player,"");
        MessagesManager.sendCenteredMessage(player, "/sws loadworld (Folder Name or World Name) YOU CAN USE TO GO THIS WORLD");
        MessagesManager.sendCenteredMessage(player,"/sws setminplayers (Number)");
        MessagesManager.sendCenteredMessage(player,"/sws setmapid (Map ID)");
        MessagesManager.sendCenteredMessage(player,"/sws setmapname (Map Name)");
        MessagesManager.sendCenteredMessage(player,"/sws setmode (NORMAL OR INSANE)");
        MessagesManager.sendCenteredMessage(player,"/sws settype (SOLO OR TEAM)");
        MessagesManager.sendCenteredMessage(player,"/sws addspawn");
        MessagesManager.sendCenteredMessage(player,"If is Team");
        MessagesManager.sendCenteredMessage(player,"/sws setlobby");
        MessagesManager.sendCenteredMessage(player,"/sws setlobbypos1");
        MessagesManager.sendCenteredMessage(player,"/sws setlobbypos2");
    }

}
