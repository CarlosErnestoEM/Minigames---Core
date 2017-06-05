package com.pollogamer.minigames.poshosw.listener;

import com.pollogamer.minigames.core.enums.GameState;
import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.utils.ArenaUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        SWArena swArena = Principal.getPlugin().getArenaManager().getArena(player);
        if(swArena != null){
            if(swArena.getGameState().equals(GameState.WAITING)||swArena.getGameState().equals(GameState.STARTING)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        SWArena swArena = Principal.getPlugin().getArenaManager().getArena(player);
        if(swArena != null){
            if(swArena.getGameState().equals(GameState.WAITING)||swArena.getGameState().equals(GameState.STARTING)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLevelChange(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            SWArena swArena = Principal.getPlugin().getArenaManager().getArena(player);
            if(swArena != null){
                if(swArena.getGameState().equals(GameState.WAITING)||swArena.getGameState().equals(GameState.STARTING)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            SWArena swArena = Principal.getPlugin().getArenaManager().getArena(player);
            if(swArena != null){
                if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                    if(!swArena.isFallDamage()){
                        event.setCancelled(true);
                    }
                }else{
                    if(swArena.getGameState().equals(GameState.WAITING)||swArena.getGameState().equals(GameState.STARTING)){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(player.getKiller() != null){
            Player killer = player.getKiller();
            SWArena arenap1 = Principal.getPlugin().getArenaManager().getArena(player);
            SWArena arenap2 = Principal.getPlugin().getArenaManager().getArena(killer);
            if(arenap1 != null && arenap2 != null){
                arenap2.addKill(killer);
                event.setDeathMessage(null);
                arenap2.broadcast("&7"+player.getName()+" &efue asesinado por &7"+killer.getName());
                ArenaUtils.removePlayer(player);
                arenap2.checkWin();
            }
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event){

    }
}
