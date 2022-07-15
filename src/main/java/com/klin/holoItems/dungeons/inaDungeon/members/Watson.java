package com.klin.holoItems.dungeons.inaDungeon.members;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.dungeons.inaDungeon.Maintenance;
import com.klin.holoItems.utility.Task;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Watson extends Member {
    public Location to;
    public Location from;
    public int taskId;
    private final Set<LivingEntity> airborne;

    public Watson(Player player){
        super(player, BarColor.YELLOW);
        taskId = -1;
        airborne = new HashSet<>();
    }

    public void ability(double angle, PlayerInteractEvent event) {
        if(!cooldown)
            return;
        cooldown = false;
        if(taskId!=-1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        double distance = to.distance(from);
        if(distance==0)
            return;
        double abs = Math.abs(angle);
        if(abs<Math.PI*0.5 || abs>Math.PI*1.2)
            return;
        Maintenance maintenance = (Maintenance) InaDungeon.presets.get("maintenance");
        maintenance.inputs.replace(player, Map.entry(maintenance.inputs.get(player).getKey(), 0.0));
        World world = player.getWorld();
        Set<LivingEntity> targets = new HashSet<>();
        LivingEntity living;
        RayTraceResult result;
        Vector dir = to.subtract(from).toVector();
        while((result = world.rayTraceEntities(from, dir, distance, 1, entity -> (entity instanceof LivingEntity && !targets.contains(entity) && entity != player && !(entity instanceof ArmorStand)))) != null &&
                (living = (LivingEntity) result.getHitEntity()) != null)
            targets.add(living);
        dir.normalize().multiply(0.2);
        new Task(HoloItems.getInstance(), 1, 1){
            double increment = 0;
            final Location loc = from.clone().add(0, 1, 0);
            public void run(){
                for(int i=0; i<6; i++){
                    if(increment>=distance){
                        cancel();
                        return;
                    }
                    world.spawnParticle(Particle.REDSTONE, loc.add(dir), 1, new Particle.DustOptions(Color.WHITE, 1));
                    increment += 0.2;
                }
            }
        };
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Command: Rend"));
        if(targets.isEmpty())
            return;
        for(LivingEntity target : targets){
            if(!airborne.contains(target)){
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6, 6));
                new Task(HoloItems.getInstance(), 6, 1){
                    int increment = 0;
                    boolean flip = true;
                    public void run(){
                        if(increment>=1200 || !target.isValid() || target.isDead()){
                            airborne.remove(target);
                            cancel();
                            return;
                        }
                        if(!flip && target.isOnGround()){
                            airborne.remove(target);
                            target.setCustomName(null);
                            target.setVelocity(new Vector(0, 0.45, 0));
                            cancel();
                            return;
                        }
                        if(flip && target.getVelocity().getY()<0) {
                            target.setNoDamageTicks(0);
                            target.damage(15, player);
                            target.setNoDamageTicks(0);
                            target.setCustomName("Dinnerbone");
                            flip = false;
                        }
                        increment++;
                    }
                };
            }
            new BukkitRunnable() {
                public void run() {
                    target.setVelocity(new Vector(0, 0.75, 0));
                }
            }.runTaskLater(HoloItems.getInstance(), 6);
        }
    }

    public void attack(EntityDamageByEntityEvent event, Entity entity) {

    }

    public void defend(EntityDamageByEntityEvent event, Entity damager) {

    }

    public void burst(int charge) {

    }
}
