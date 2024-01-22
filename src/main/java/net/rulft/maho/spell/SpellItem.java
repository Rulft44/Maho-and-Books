package net.rulft.maho.spell;

import net.minecraft.world.item.Item;


public abstract class SpellItem extends Item {
    public SpellItem(Item.Properties properties) {
        super(properties);
    }

    public abstract String getSpellType();


}
