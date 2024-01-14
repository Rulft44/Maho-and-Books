package net.rulft.maho.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FireBookItem extends Item {
    public FireBookItem(Properties pProperties) {
        super(pProperties);
    }

    Boolean hasFireball = null;
    Boolean hasCombustion = null;
    Boolean hasHeatCloud = null;
    Boolean hasMiniSun = null;
    Boolean hasDawn = null;
    Boolean hasSecondHorseman = null;

    private static final long COOLDOWN_TIME_SECONDS = 5; // 5 seconds cooldown
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);

        // Check if the item is on cooldown
        if (isOnCooldown(itemStack)) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
        }

        if (!level.isClientSide) {
            Vec3 lookVector = player.getLookAngle().normalize();

            CustomLargeFireball largeFireball = new CustomLargeFireball(EntityType.FIREBALL, level, player, player.getX(), player.getEyeY(), player.getZ(), lookVector.x, lookVector.y, lookVector.z);

            double speed = 1.5;
            largeFireball.setDeltaMovement(lookVector.x * speed, lookVector.y * speed, lookVector.z * speed);

            level.addFreshEntity(largeFireball);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));

            // Set the cooldown for the item
            setCooldown(itemStack);

            if (!player.isCreative()) {
                itemStack.hurt(1, level.random, null);
            }

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
    }

    // Check if the item is on cooldown
    private boolean isOnCooldown(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long cooldownEnd = nbt.getLong("cooldownEnd");

        return currentTime < cooldownEnd;
    }

    // Set the cooldown for the item
    private void setCooldown(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        long cooldownEnd = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + COOLDOWN_TIME_SECONDS;
        nbt.putLong("cooldownEnd", cooldownEnd);
    }


//Tooltip
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.maho.fire_book.tooltip"));
    }
}
