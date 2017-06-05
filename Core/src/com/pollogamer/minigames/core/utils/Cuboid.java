package com.pollogamer.minigames.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

public class Cuboid
        implements Cloneable, ConfigurationSerializable, Iterable<Block>
{
    protected String worldName;
    protected final Vector minimumPoint;
    protected final Vector maximumPoint;

    public Cuboid(Cuboid cuboid){
        this(cuboid.worldName, cuboid.minimumPoint.getX(), cuboid.minimumPoint.getY(), cuboid.minimumPoint.getZ(), cuboid.maximumPoint.getX(), cuboid.maximumPoint.getY(), cuboid.maximumPoint.getZ());
    }

    public Cuboid(Location loc){
        this(loc, loc);
    }

    public Cuboid(Location loc1, Location loc2){
        if ((loc1 != null) && (loc2 != null)){
            if ((loc1.getWorld() != null) && (loc2.getWorld() != null)){
                if (!loc1.getWorld().getUID().equals(loc2.getWorld().getUID())) {
                    throw new IllegalStateException("¡El punto 1 y 2 del cuboid no pueden ser las mismas!");
                }
            }
            else {
                throw new NullPointerException("¡Algunos de las localizaciones del Cuboid del spawn fueron nulas!");
            }
            worldName = loc1.getWorld().getName();

            double xPos1 = Math.min(loc1.getX(), loc2.getX());
            double yPos1 = Math.min(loc1.getY(), loc2.getY());
            double zPos1 = Math.min(loc1.getZ(), loc2.getZ());
            double xPos2 = Math.max(loc1.getX(), loc2.getX());
            double yPos2 = Math.max(loc1.getY(), loc2.getY());
            double zPos2 = Math.max(loc1.getZ(), loc2.getZ());
            minimumPoint = new Vector(xPos1, yPos1, zPos1);
            maximumPoint = new Vector(xPos2, yPos2, zPos2);
        }
        else
        {
            throw new NullPointerException("¡Algunos de las localizaciones del Cuboid del spawn fueron nulas!");
        }
    }

    public Cuboid(String worldName, double x1, double y1, double z1, double x2, double y2, double z2){
        if ((worldName == null) || (Bukkit.getServer().getWorld(worldName) == null)) {
            throw new NullPointerException("¡Algunos de las localizaciones del Cuboid del spawn fueron nulas!");
        }
        worldName = worldName;

        double xPos1 = Math.min(x1, x2);
        double xPos2 = Math.max(x1, x2);
        double yPos1 = Math.min(y1, y2);
        double yPos2 = Math.max(y1, y2);
        double zPos1 = Math.min(z1, z2);
        double zPos2 = Math.max(z1, z2);
        minimumPoint = new Vector(xPos1, yPos1, zPos1);
        maximumPoint = new Vector(xPos2, yPos2, zPos2);
    }

    public boolean containsLocation(Location location){
        return (location != null) && (location.toVector().isInAABB(minimumPoint, maximumPoint));
    }

    public boolean containsVector(Vector vector){
        return (vector != null) && (vector.isInAABB(minimumPoint, maximumPoint));
    }

    public List<Block> getBlocks(){
        List<Block> blockList = new ArrayList();
        World world = getWorld();
        if (world != null) {
            for (int x = minimumPoint.getBlockX(); x <= maximumPoint.getBlockX(); x++) {
                for (int y = minimumPoint.getBlockY(); (y <= maximumPoint.getBlockY()) && (y <= world.getMaxHeight()); y++) {
                    for (int z = minimumPoint.getBlockZ(); z <= maximumPoint.getBlockZ(); z++) {
                        blockList.add(world.getBlockAt(x, y, z));
                    }
                }
            }
        }
        return blockList;
    }

    public Location getLowerLocation(){
        return minimumPoint.toLocation(getWorld());
    }

    public double getLowerX(){
        return minimumPoint.getX();
    }

    public double getLowerY(){
        return minimumPoint.getY();
    }

    public double getLowerZ(){
        return minimumPoint.getZ();
    }

    public Location getUpperLocation(){
        return maximumPoint.toLocation(getWorld());
    }

    public double getUpperX(){
        return maximumPoint.getX();
    }

    public double getUpperY(){
        return maximumPoint.getY();
    }

    public double getUpperZ(){
        return maximumPoint.getZ();
    }

    public double getVolume(){
        return (getUpperX() - getLowerX() + 1.0D) * (getUpperY() - getLowerY() + 1.0D) * (getUpperZ() - getLowerZ() + 1.0D);
    }

    public World getWorld(){
        World world = Bukkit.getServer().getWorld(worldName);
        if (world == null){
            throw new NullPointerException("World '" + worldName + "' is not loaded.");
        }
        return world;
    }

    public void setWorld(World world){
        if (world != null){
            worldName = world.getName();
        }else{
            throw new NullPointerException("The world cannot be null.");
        }
    }

    public Cuboid clone(){
        return new Cuboid(this);
    }

    public ListIterator<Block> iterator(){
        return getBlocks().listIterator();
    }

    public Map<String, Object> serialize()
    {
        Map<String, Object> serializedCuboid = new HashMap();
        serializedCuboid.put("worldName", worldName);
        serializedCuboid.put("x1", Double.valueOf(minimumPoint.getX()));
        serializedCuboid.put("x2", Double.valueOf(maximumPoint.getX()));
        serializedCuboid.put("y1", Double.valueOf(minimumPoint.getY()));
        serializedCuboid.put("y2", Double.valueOf(maximumPoint.getY()));
        serializedCuboid.put("z1", Double.valueOf(minimumPoint.getZ()));
        serializedCuboid.put("z2", Double.valueOf(maximumPoint.getZ()));
        return serializedCuboid;
    }

    public static Cuboid deserialize(Map<String, Object> serializedCuboid)
    {
        try
        {
            String worldName = (String)serializedCuboid.get("worldName");

            double xPos1 = ((Double)serializedCuboid.get("x1")).doubleValue();
            double xPos2 = ((Double)serializedCuboid.get("x2")).doubleValue();
            double yPos1 = ((Double)serializedCuboid.get("y1")).doubleValue();
            double yPos2 = ((Double)serializedCuboid.get("y2")).doubleValue();
            double zPos1 = ((Double)serializedCuboid.get("z1")).doubleValue();
            double zPos2 = ((Double)serializedCuboid.get("z2")).doubleValue();

            return new Cuboid(worldName, xPos1, yPos1, zPos1, xPos2, yPos2, zPos2);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
