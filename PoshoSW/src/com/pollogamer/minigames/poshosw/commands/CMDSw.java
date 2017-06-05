package com.pollogamer.minigames.poshosw.commands;

import com.pollogamer.minigames.core.arena.Arena;
import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.core.utils.LocationUtils;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.config.ConfigManager;
import com.pollogamer.minigames.poshosw.task.LobbyTask;
import com.pollogamer.minigames.poshosw.utils.ArenaUtils;
import com.pollogamer.minigames.poshosw.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSw implements CommandExecutor {

    public String prefix = "§e§lSkyWars §7» §a";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("The command only can be executed by the player");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("skywars.command.skywars")){
            //send no perms
            return true;
        }
        if(args.length == 0){
            sendHelpPage(player);
        }else if(args.length == 1){
            switch (args[0].toLowerCase()){
                case "start":
                    if(!player.hasPermission("skywars.command.skywars.start")){
                        //send no perms
                        break;
                    }
                    SWArena swArena = Principal.getPlugin().getArenaManager().getArena(player);
                    if(swArena == null){
                        player.sendMessage(prefix+"No estas en una arena pndjo, usa /sw start (Arena)");
                    }else{
                        if(!swArena.isStarting()||!swArena.isStarted()){
                            if(swArena.getPlayers().size() == 1){
                                player.sendMessage(prefix+"No seas pendejo compa... Como lo vas a iniciar con 1 persona");
                            }else{
                                swArena.lobbyTask = new LobbyTask(swArena);
                                swArena.setForcestart(true);
                                swArena.setStarting(true);
                                swArena.setGameState(GameState.STARTING);
                                swArena.broadcast("&eEsta mamada se forzo a iniciar");
                            }
                        }else{
                            player.sendMessage(prefix+"Esta mamada ya esta en juego we");
                        }
                    }
                    break;
                case "joinrandom":

                    break;
                case "leave":
                    SWArena swArenap = Principal.getPlugin().getArenaManager().getArena(player);
                    if(swArenap == null){
                        player.sendMessage(prefix+"No estas en 1 arena we xd");
                    }else{
                        ArenaUtils.removePlayer(player);
                    }
                    break;
                case "join":
                    SWArena swArena1 = Principal.getPlugin().getArenaManager().getArena(player);
                    if(swArena1 != null){
                        player.sendMessage(prefix+"Ya estas en 1 arena we xd");
                    }else{

                    }
                    //gui selector
                    break;
                case "list":
                    player.sendMessage("§cLista de arenas:");
                    for(Arena arena : Principal.getPlugin().getArenaManager().getArenas()){
                        player.sendMessage("§a"+arena.getName());
                    }
                    break;
                case "setspawn":
                    ConfigManager.config.set("locations.lobby", LocationUtils.serializeLoc(player.getLocation()));
                    ConfigManager.config.save();
                    player.sendMessage(prefix+"Has marcado el spawn!");
                    Lang.start();
                    break;
                default:
                    sendHelpPage(sender);
                    break;
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("start")){
                SWArena swArena = Principal.getPlugin().getArenaManager().getArena(args[1]);
                if(swArena != null){
                    if(!swArena.isStarting()||!swArena.isStarted()){
                        if(swArena.getPlayers().size() == 1){
                            player.sendMessage(prefix+"No seas pendejo compa... Como lo vas a iniciar con 1 persona");
                        }else{
                            swArena.lobbyTask = new LobbyTask(swArena);
                            swArena.setForcestart(true);
                            swArena.setStarting(true);
                            swArena.setGameState(GameState.STARTING);
                            swArena.broadcast("&eEsta mamada se forzo a iniciar");
                        }
                    }else{
                        player.sendMessage(prefix+"Esta mamada ya esta en juego we");
                    }
                }else{
                    player.sendMessage(prefix+"La arena "+args[1]+" no existe idiota xd");
                }
            }else if(args[0].equalsIgnoreCase("join")){
                SWArena playerArena = Principal.getPlugin().getArenaManager().getArena(player);
                if(playerArena == null){
                    SWArena swArena = Principal.getPlugin().getArenaManager().getArena(args[1]);
                    if(swArena != null){
                        ArenaUtils.addPlayer(player,swArena.getArenaid());
                    }else{
                        player.sendMessage(prefix+"La arena "+args[1]+" no existe idiota xd");
                    }
                }else{
                    player.sendMessage(prefix+"Ya estas en 1 arena wey");
                }
            }
        }
        return true;
    }


    public void sendHelpPage(CommandSender commandSender){
        commandSender.sendMessage("/sw start");
    }


}
