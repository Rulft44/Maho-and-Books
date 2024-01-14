package net.rulft.maho.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rulft.maho.MahoMod;
import net.rulft.maho.item.custom.FireBookItem;
import net.rulft.maho.item.custom.UraniumItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MahoMod.MOD_ID);


    public static final RegistryObject<Item> URANIUM = ITEMS.register("uranium",
            () -> new UraniumItem(new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).food(ModFoods.URANIUM)));

    public static final RegistryObject<Item> FIRE_BOOK = ITEMS.register("fire_book",
            () -> new FireBookItem(new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).stacksTo(1).fireResistant().rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
