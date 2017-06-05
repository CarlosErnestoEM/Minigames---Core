package com.pollogamer.minigames.poshosw.listener;

import com.pollogamer.minigames.poshosw.Principal;
import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.sign.SkyWarsSign;
import com.pollogamer.minigames.poshosw.utils.ArenaUtils;
import com.pollogamer.minigames.poshosw.utils.SignUtils;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener{

    @EventHandler
    public void onSignEdit(SignChangeEvent event){
        if(event.getPlayer().hasPermission("poshoskywars.sign.create")){
            if(event.getLine(0).equalsIgnoreCase("[SkyWars]")){
                if(Principal.getPlugin().getArenaManager().getArena(event.getLine(1)) != null){
                    new SkyWarsSign(event.getBlock().getLocation(),event.getLine(1));
                    SignUtils.createSignBuildUHC(event.getLine(1),event.getBlock().getLocation());
                    event.getPlayer().sendMessage("Created sign for arena "+event.getLine(1));
                }else{
                    event.getBlock().breakNaturally();
                    event.getPlayer().sendMessage("The arena "+event.getLine(1) + " not existing!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign s = (Sign) e.getClickedBlock().getState();
                SkyWarsSign sign = SignUtils.getSignArena(s.getLocation());
                if (sign != null) {
                    SWArena swArena = Principal.getPlugin().getArenaManager().getArena(p);
                    if(swArena == null){
                        ArenaUtils.addPlayer(p,sign.getName());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent event){
        Player p = event.getPlayer();
        if(event.getBlock().getType().equals(Material.SIGN)||event.getBlock().getType().equals(Material.SIGN_POST)||event.getBlock().getType().equals(Material.WALL_SIGN)){
            SkyWarsSign sign = SignUtils.getSignArena(event.getBlock().getLocation());
            if(sign != null){
                if(p.hasPermission("poshoskywars.sign.break")){
                    SkyWarsSign.signs.remove(sign);
                    SignUtils.deleteSignBuildUHC(sign.getLocation(),sign.getName());
                    p.sendMessage("You delete sign for arena "+sign.getName());
                }else{
                    event.setCancelled(true);
                }
            }
        }
    }

}
