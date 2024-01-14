package net.rulft.maho.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;

public class ModCreativeModeTab {
    public static final CreativeModeTab DEBUG_TAB = new CreativeModeTab("debugtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.COMMAND_BLOCK);
        }
    };


}