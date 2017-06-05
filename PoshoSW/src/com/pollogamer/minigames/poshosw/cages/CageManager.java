package com.pollogamer.minigames.poshosw.cages;


import com.pollogamer.minigames.poshosw.arena.SWArena;
import com.pollogamer.minigames.poshosw.enums.ArenaType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CageManager {

    public static void createCage(ArenaType arenaType,Location location) {
        if(arenaType.equals(ArenaType.SOLO)){
            World world = location.getWorld();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            world.getBlockAt(x, y, z).setType(Material.GLASS);
            world.getBlockAt(x, y + 1, z + 1).setType(Material.GLASS);
            world.getBlockAt(x, y + 1, z - 1).setType(Material.GLASS);
            world.getBlockAt(x + 1, y + 1, z).setType(Material.GLASS);
            world.getBlockAt(x - 1, y + 1, z).setType(Material.GLASS);
            world.getBlockAt(x, y + 2, z + 1).setType(Material.GLASS);
            world.getBlockAt(x, y + 2, z - 1).setType(Material.GLASS);
            world.getBlockAt(x + 1, y + 2, z).setType(Material.GLASS);
            world.getBlockAt(x - 1, y + 2, z).setType(Material.GLASS);
            world.getBlockAt(x, y + 3, z + 1).setType(Material.GLASS);
            world.getBlockAt(x, y + 3, z - 1).setType(Material.GLASS);
            world.getBlockAt(x + 1, y + 3, z).setType(Material.GLASS);
            world.getBlockAt(x - 1, y + 3, z).setType(Material.GLASS);
            world.getBlockAt(x, y + 4, z).setType(Material.GLASS);
        }
    }

    public static void removeCage(Location location) {
        location.setY(location.getBlockY()-1);
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        world.getBlockAt(x, y, z).setType(Material.AIR);
        world.getBlockAt(x, y + 1, z + 1).setType(Material.AIR);
        world.getBlockAt(x, y + 1, z - 1).setType(Material.AIR);
        world.getBlockAt(x + 1, y + 1, z).setType(Material.AIR);
        world.getBlockAt(x - 1, y + 1, z).setType(Material.AIR);
        world.getBlockAt(x, y + 2, z + 1).setType(Material.AIR);
        world.getBlockAt(x, y + 2, z - 1).setType(Material.AIR);
        world.getBlockAt(x + 1, y + 2, z).setType(Material.AIR);
        world.getBlockAt(x - 1, y + 2, z).setType(Material.AIR);
        world.getBlockAt(x, y + 3, z + 1).setType(Material.AIR);
        world.getBlockAt(x, y + 3, z - 1).setType(Material.AIR);
        world.getBlockAt(x + 1, y + 3, z).setType(Material.AIR);
        world.getBlockAt(x - 1, y + 3, z).setType(Material.AIR);
        world.getBlockAt(x, y + 4, z).setType(Material.AIR);
    }

    public static void removeAllCages(SWArena swArena){
        for(Player player : swArena.getPlayers()){
            swArena.removeCage(player);
        }
    }
}
