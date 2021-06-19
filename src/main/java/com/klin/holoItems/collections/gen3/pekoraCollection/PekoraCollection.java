package com.klin.holoItems.collections.gen3.pekoraCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.pekoraCollection.items.CarrotCannon;
import com.klin.holoItems.collections.gen3.pekoraCollection.items.Compactor;
import com.klin.holoItems.collections.gen3.pekoraCollection.items.DoubleUp;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PekoraCollection extends Collection {
    public static final String name = "Pekora";
    public static final String desc = "";
    public static final String theme = "War crimes committed";
    public static final String ign = "usadapekora";
    public static final String uuid = "3e255051-4ea8-465a-af46-28f019ec7ebb";

    public static final char key = 's';

    public PekoraCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(DoubleUp.key, new DoubleUp());
        collection.put(CarrotCannon.key, new CarrotCannon());
        collection.put(Compactor.key, new Compactor());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("100 Players killed", player.getStatistic(Statistic.PLAYER_KILLS)/100);
        stat.put("Villagers killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.VILLAGER));
        stat.put("Wandering Traders killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.WANDERING_TRADER));
        stat.put("Iron Golems killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.IRON_GOLEM));
        stat.put("Snow Golems killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SNOWMAN));

        return stat;
    }
}
