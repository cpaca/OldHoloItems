package com.klin.holoItems.collections.en.inaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en.inaCollection.items.*;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class InaCollection extends Collection {
    public static final String name = "Ina";
    public static final String desc = "";
    public static final String theme = "Mats farmed";
    public static final String ign = "ninoina";
    public static final String uuid = "8237e9c4-1c11-4c19-b29a-67c51f2045b8";

    public static final char key = 'L';

    public InaCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(CorruptedWheat.key, new CorruptedWheat());
        collection.put(EarthenSpoon.key, new EarthenSpoon());
        collection.put(CeramicLadle.key, new CeramicLadle());
        collection.put(WateringCan.key, new WateringCan());
        collection.put(Ingraining.key, new Ingraining());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Wheat harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.WHEAT));
        stat.put("Beetroot harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.BEETROOT));
        stat.put("Carrots harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.CARROT));
        stat.put("Potato harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.POTATO));
        stat.put("Bamboo harvested, halved", player.getStatistic(Statistic.BREAK_ITEM, Material.BAMBOO)/2);
        stat.put("Cocoa harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.COCOA_BEANS));
        stat.put("Berries harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.SWEET_BERRIES));
        stat.put("Nether Warts harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.NETHER_WART));

        return stat;
    }
}
