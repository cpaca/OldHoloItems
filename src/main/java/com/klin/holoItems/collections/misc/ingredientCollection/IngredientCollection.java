package com.klin.holoItems.collections.misc.ingredientCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.ingredientCollection.items.EmeraldLeaf;
import org.bukkit.entity.Player;

import java.util.Map;

public class IngredientCollection extends Collection {
    public static final String name = "Ingredient";
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;

    public static final char key = '0';

    public IngredientCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(EmeraldLeaf.key, new EmeraldLeaf());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
