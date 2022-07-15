package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.hidden.franCollection.items.SharpenedFangs;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class Minesweeper implements Listener, Resetable {
    //minesweeper -27 -9 -294 -262 59
    private final Set<Material> sand;
    public final Map<Integer, Material> flags;
    private final Map<Map.Entry<Integer, Integer>, Boolean> squares;
    private final int y;
    private final Map<Block, BlockData> reset;

    public Minesweeper(World world, int x1, int x2, int z1, int z2, int y1){
        sand = Stream.of(Material.SAND, Material.SANDSTONE_SLAB, Material.SANDSTONE_STAIRS, Material.SMOOTH_SANDSTONE_SLAB, Material.SMOOTH_SANDSTONE).collect(Collectors.toCollection(HashSet::new));
        flags = new HashMap<>() {{
            put(0, Material.ORANGE_STAINED_GLASS);
            put(1, Material.RED_SAND);
            put(2, Material.RED_SANDSTONE_SLAB);
            put(3, Material.RED_SANDSTONE_STAIRS);
            put(4, Material.RED_SANDSTONE);
        }};
        squares = new HashMap<>();
        reset = new HashMap<>();
        for(int x=x1; x<=x2; x++){
            for(int z=z1; z<z2; z++){
                Block block = world.getBlockAt(x, y1, z);
                do{
                    if(sand.contains(block.getType())) {
                        squares.put(Map.entry(x, z), Math.random() < 0.25);
                        break;
                    }
                    block = block.getRelative(BlockFace.UP);
                }while(!block.isEmpty());
            }
        }
        y = y1;
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler
    public void mine(BlockBreakEvent event){
//        event.setCancelled(true);
        Block block = event.getBlock();
        Map.Entry<Integer, Integer> key = Map.entry(block.getX(), block.getZ());
        Boolean mine = squares.get(key);
        Material material = block.getType();
        if(mine==null || !sand.contains(material))
            return;
        reset.putIfAbsent(block, block.getBlockData());
        if(mine){
            block.setType(Material.ORANGE_GLAZED_TERRACOTTA);
            World world = block.getWorld();
            Player player = event.getPlayer();
            Location loc = player.getLocation();
            if(reset.size()<6) {
                world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 4, 1);
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 8));
            Entity entity = world.spawnEntity(loc, EntityType.EVOKER_FANGS);
            entity.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, SharpenedFangs.name+":20");
            world.playSound(loc, Sound.AMBIENT_CAVE, 4, 1);
            return;
        }
        block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.SAND));
        int adjacent = 0;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                Map.Entry<Integer, Integer> clone = Map.entry(key.getKey()+i, key.getValue()+j);
                Boolean present = squares.get(clone);
                if(present!=null && present) {
                    if(adjacent>=4){
                        Random random = new Random();
                        Map.Entry<Integer, Integer> shift = key;
                        while(squares.get(shift)==null || !squares.get(shift))
                            shift = Map.entry(key.getKey()+random.nextInt(3) - 1, key.getValue()+random.nextInt(3) - 1);
                        squares.replace(shift, false);
                        Block center = block.getWorld().getBlockAt(key.getKey(), y, key.getValue());
                        for(BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST}){
                            Block check = center.getRelative(face);
                            do{
                                Material type = check.getType();
                                if(flags.containsValue(type)) {
                                    //reverse map
                                    int integer = 0;
                                    for(Integer flag : flags.keySet()){
                                        if(flags.get(flag)==type)
                                            integer = flag;
                                    }
                                    check.setType(flags.get(integer==0?0:integer-1));
                                }
                                check = check.getRelative(BlockFace.UP);
                            }while(!check.isEmpty());
                        }
                    }
                    else
                        adjacent++;
                }
            }
        }
        block.setType(flags.get(adjacent));
    }

    public void reset(){
        BlockBreakEvent.getHandlerList().unregister(this);
        for(Block block : reset.keySet())
            block.setBlockData(reset.get(block));
    }
}
