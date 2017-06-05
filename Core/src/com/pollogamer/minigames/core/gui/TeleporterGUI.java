package com.pollogamer.minigames.core.gui;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.minigames.core.CorePlugin;
import com.pollogamer.minigames.core.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TeleporterGUI extends GUIPage<CorePlugin>{

    private BukkitTask bukkitTask;
    private Arena arena;

    public TeleporterGUI(CorePlugin corePlugin, Player player, Arena arena) {
        super(corePlugin, player, "Teleporter", ((int) Math.ceil(arena.getPlayers().size() / 9.0)) * 9);
        this.arena = arena;
        build();
        bukkitTask = new BukkitRunnable(){
            @Override
            public void run() {
                refresh();
            }
        }.runTaskTimer(corePlugin,5,5);
    }

    @Override
    public void buildPage() {
        int slot = 0;
        for(Player player : arena.getPlayers()){
            ItemStackBuilder itemStackBuilder = new ItemStackBuilder().setSkullOwner(player.getName()).setName("§7"+ player.getName()).setLore("§7Health: §f"+(int)(player.getHealth()*2)+"%","§7Food: §f"+ (int)(player.getFoodLevel()*5)," ","§7¡Click para espectar!");
            addButton(new SimpleButton(itemStackBuilder).setAction(((plugin1, player1, page) -> {
                if(arena.getPlayers().contains(player)){
                    //tp
                }else{
                    player.sendMessage("§c¡Lo sentimos, no hemos podido teletransportarte hacia ese jugador!");
                }
            })),slot++);
        }
    }

    @Override
    public void destroy() {
        bukkitTask.cancel();
    }
}
