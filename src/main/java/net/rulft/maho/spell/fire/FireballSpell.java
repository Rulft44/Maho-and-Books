// FireballSpell.java
package net.rulft.maho.spell.fire;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.rulft.maho.item.custom.CustomLargeFireball;
import net.rulft.maho.spell.Spell;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FireballSpell extends Item implements Spell{

    public FireballSpell(Properties pProperties) {
        super(pProperties);
    }


    //Here change the number for the cooldown in seconds
    int cooldownTime = 5;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);

        if (!level.isClientSide) {
            // Cast the spell
            castWithEffect(player, 1.0); // Initial multiplier is 1.0

            if (!player.isCreative()) {
                itemStack.hurt(1, level.random, null);
            }

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
    }

    // Implementation of Spell interface
    @Override
    public void cast(Player caster) {
        castWithEffect(caster, 1.0); // Default multiplier is 1.0
    }

    @Override
    public void castWithEffect(Player caster, double effectMultiplier) {
        Vec3 lookVector = caster.getLookAngle().normalize();

        CustomLargeFireball largeFireball = new CustomLargeFireball(EntityType.FIREBALL, caster.level, caster, caster.getX(), caster.getEyeY(), caster.getZ(), lookVector.x, lookVector.y, lookVector.z);

        double speed = 1.5 * effectMultiplier; // Apply the effect multiplier to the speed
        largeFireball.setDeltaMovement(lookVector.x * speed, lookVector.y * speed, lookVector.z * speed);

        caster.level.addFreshEntity(largeFireball);
        caster.level.playSound((Player) null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.GHAST_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (caster.level.random.nextFloat() * 0.4F + 0.8F));

        caster.getCooldowns().addCooldown(this, cooldownTime * 20);
    }

    // Tooltip
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.maho.fireball_scroll.tooltip"));
    }
}
