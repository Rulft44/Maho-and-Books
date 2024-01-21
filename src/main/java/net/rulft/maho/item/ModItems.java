package net.rulft.maho.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rulft.maho.MahoMod;
import net.rulft.maho.item.custom.grimoire.FireBook;
import net.rulft.maho.spell.fire.FireballSpell;
import net.rulft.maho.spell.fire.HeatCloudSpell;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MahoMod.MOD_ID);


    //------------------ Spells ------------------
    public static final RegistryObject<Item> FIREBALL_SPELL = ITEMS.register("fireball_spell",
            () -> new FireballSpell(new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> HEATCLOUD_SPELL = ITEMS.register("heatcloud_spell",
            () -> new HeatCloudSpell(new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.COMMON)));


    //------------------ Grimoires ------------------
    public static final RegistryObject<Item> FIRE_BOOK = ITEMS.register("fire_book", () ->
            new FireBook("fire", new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.EPIC)));

    //------------------ Misc Items ------------------


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
