package net.rulft.maho.item.custom.grimoire;

import net.minecraft.world.item.Item;


public abstract class GrimoireItem extends Item {
    public GrimoireItem(Item.Properties properties) {
        super(properties);
    }

    public abstract String getGrimoireType();


}
