package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class SteeringWheel extends Item implements Interactable {
    public static final String name = "steeringWheel";
    private final Map<Player, Map.Entry<Vector, Integer>> drivers;

    private static final Material material = Material.MUSIC_DISC_STAL;
    private static final int quantity = 1;
    private static final String lore =
            "Drift";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public SteeringWheel(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        drivers = new HashMap<>();
    }

    public void registerRecipes() {

    }

    public void ability(PlayerInteractEvent event, Action action){
        Player player = event.getPlayer();
        Map.Entry<Vector, Integer> driver = drivers.get(player);
        if(driver!=null) {
            drivers.replace(player, Map.entry(driver.getKey(), driver.getValue()+1));
            return;
        } else
            drivers.put(player, Map.entry(player.getLocation().getDirection().setY(0).normalize(), 0));
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            int offset = 0;
            Vector drift = null;
            public void run(){
                Map.Entry<Vector, Integer> driver = drivers.get(player);
                Integer ticks = driver.getValue();
                if(increment>1200 || offset>ticks){
                    drivers.remove(player);
                    cancel();
                    return;
                }
                Vector steer = driver.getKey();
                Vector dir = player.getLocation().getDirection().setY(0).normalize();
                double angle = steer.clone().crossProduct(dir).getY();
                double cap = drift==null?0.05:Math.max(0, drift.clone().crossProduct(dir).getY()+0.1);
                if(cap>0)
                    cap/=2;
                if(angle<0)
                    angle = Math.max(angle, -cap);
                else
                    angle = Math.min(angle, cap);
                steer.rotateAroundY(angle);
                if(player.isSneaking()){
                    if(drift==null)
                        drift = dir;
                } else
                    drift = null;
                player.setVelocity(steer);
                increment++;
                if(increment%4==0)
                    offset++;
            }
        };
    }
}
