package com.klin.holoItems.collections.gen0.suiseiCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Comet;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Hoshiyumi;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.RolledNewspaper;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.SuperStar;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class SuiseiCollection extends Collection {
    public static final String name = "Suisei";
    public static final String desc =
            "It's your shooting star, your" +"/n"+
            "diamond in the rough, idol" +"/n"+
            "VTuber, Hoshimachi Suisei!";
    public static final String theme = "Demons slaughtered";
    public static final String ign = "suisei_hosimati";
    public static final String uuid = "a771cbb5-6533-4e27-ab2f-5fd519394487";

    public static final char key = 'a';

    public SuiseiCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(SuperStar.key, new SuperStar());
        collection.put(Hoshiyumi.key, new Hoshiyumi());
        collection.put(Comet.key, new Comet());
        collection.put(RolledNewspaper.key, new RolledNewspaper());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Blazes slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.BLAZE));
        stat.put("Ghasts slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.GHAST));
        stat.put("Hoglins slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.HOGLIN));
        stat.put("Magma Cubes slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.MAGMA_CUBE));
        stat.put("Piglins slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.PIGLIN));
        stat.put("Piglin Brutes slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.PIGLIN_BRUTE));
        stat.put("Striders slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.STRIDER));
        stat.put("Withers slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER));
        stat.put("Wither Skeletons slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER_SKELETON));
        stat.put("Zoglins slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOGLIN));
        stat.put("Zombified Piglins slain", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIFIED_PIGLIN));

        return stat;
    }
}
