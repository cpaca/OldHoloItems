package com.klin.holoItems.collections.misc.klinCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.klinCollection.items.Bore;
import com.klin.holoItems.collections.misc.klinCollection.items.Paver;
import com.klin.holoItems.collections.misc.klinCollection.items.Trowel;
import com.klin.holoItems.collections.misc.klinCollection.items.Vacuum;
import org.bukkit.entity.Player;

import java.util.Map;

public class KlinCollection extends Collection {
    public static final String name = "Klin";
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;

    public static final char key = 'Y';

    public KlinCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(Bore.key, new Bore());
        collection.put(Trowel.key, new Trowel());
        collection.put(Paver.key, new Paver());
        collection.put(Vacuum.key, new Vacuum());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
