package net.rulft.maho.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rulft.maho.MahoMod;
import net.rulft.maho.item.custom.StrangeScrollItem;
import net.rulft.maho.item.custom.grimoire.EarthBook;
import net.rulft.maho.item.custom.grimoire.FireBook;
import net.rulft.maho.spell.fire.FireballSpell;
import net.rulft.maho.spell.fire.HeatCloudSpell;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MahoMod.MOD_ID);


    //------------------ Spells ------------------

    //--- Fire ---
    public static final RegistryObject<Item> FIREBALL_SPELL = ITEMS.register("fireball_spell",
            () -> new FireballSpell("Fire", new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.COMMON).stacksTo(1)));
    public static final RegistryObject<Item> HEATCLOUD_SPELL = ITEMS.register("heatcloud_spell",
            () -> new HeatCloudSpell("Fire", new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.COMMON).stacksTo(1)));

    //--- Lightning ---
    //--- Water ---
    //--- Earth ---
    //--- Wind ---
    //--- Dark ---

    //------------------ Grimoires ------------------
    public static final RegistryObject<Item> FIRE_BOOK = ITEMS.register("fire_book", () ->
            new FireBook("Fire", new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> EARTH_BOOK = ITEMS.register("earth_book", () ->
            new EarthBook("Earth", new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.EPIC).stacksTo(1)));


    //------------------ Misc Items ------------------
    public static final RegistryObject<Item> STRANGE_SCROLL = ITEMS.register("strange_scroll", () ->
            new StrangeScrollItem(new Item.Properties().tab(ModCreativeModeTab.DEBUG_TAB).rarity(Rarity.RARE).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
