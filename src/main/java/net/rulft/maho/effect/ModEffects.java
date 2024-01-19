package net.rulft.maho.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rulft.maho.MahoMod;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MahoMod.MOD_ID);

    public static final RegistryObject<MobEffect> SUPPRESSION = MOB_EFFECTS.register("suppresion",
            () -> new SuppressionEffect(MobEffectCategory.NEUTRAL, 4335196));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}