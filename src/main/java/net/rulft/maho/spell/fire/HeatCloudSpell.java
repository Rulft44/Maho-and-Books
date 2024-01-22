package net.rulft.maho.spell.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.rulft.maho.effect.ModEffects;
import net.rulft.maho.item.custom.grimoire.FireBook;
import net.rulft.maho.spell.Spell;
import net.rulft.maho.spell.SpellItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeatCloudSpell extends SpellItem implements Spell {

    private static final int CLOUD_RADIUS = 5;
    int cooldownTime = 3;


    public HeatCloudSpell(String spellType, Properties properties) {
        super(properties);
        this.spellType = spellType;
    }

    private String spellType;

    public String getSpellType() {
        return spellType;
    }

    @Override
    public void cast(Player caster) {
        castWithEffect(caster, 1.0); // Default multiplier is 1.0
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);

        // Check for the Suppression effect
        if (player.hasEffect(ModEffects.SUPPRESSION.get())) {
            // Return a result indicating that the item cannot be used under Suppression
            return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(usedHand));
        }



        // Check if the player is holding the Grimoire in their off hand
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (offHandStack.getItem() instanceof FireBook) {
            // Continue with the spell casting logic
            if (!level.isClientSide) {
                // Cast the spell
                castWithEffect(player, 1.0); // Initial multiplier is 1.0

                if (!player.isCreative()) {
                    itemStack.hurt(1, level.random, null);
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
            }
        }

        // If not holding the Grimoire, return a fail result
        return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
    }

    @Override
    public void castWithEffect(Player caster, double effectMultiplier) {
        Level world = caster.level;

        if (!world.isClientSide) {
            createHeatCloud(world, caster.blockPosition(), caster);
        }

        caster.getCooldowns().addCooldown(this, cooldownTime * 20);
    }

    private void createHeatCloud(Level world, BlockPos centerPos, Player caster) {
        System.out.println("Cloud created at: " + centerPos);
        int cloudRadius = CLOUD_RADIUS;
        Vec3 centerVec = new Vec3(centerPos.getX() + 0.5, centerPos.getY() + 0.5, centerPos.getZ() + 0.5);

        BlockPos.betweenClosed(centerPos.offset(-cloudRadius, 1.5, -cloudRadius), centerPos.offset(cloudRadius, -1, cloudRadius))
                .forEach(pos -> {
                    Vec3 posVec = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    if (centerVec.distanceToSqr(posVec) <= cloudRadius * cloudRadius) {
                        createFireParticles(world, pos, 10);
                    }
                });

        applyIgnitionToEntities(world, centerPos, caster);
    }



    private void createFireParticles(Level world, BlockPos pos, int particleCount) {
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) world;
            serverWorld.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, particleCount, 0.2, 0.2, 0.2, 0.1);
        }
    }

    private void applyIgnitionToEntities(Level world, BlockPos centerPos, Player caster) {
        int cloudRadius = CLOUD_RADIUS;
        AABB boundingBox = new AABB(centerPos.offset(-cloudRadius, 0, -cloudRadius), centerPos.offset(cloudRadius, 5, cloudRadius));

        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, boundingBox);

        for (LivingEntity entity : entities) {
            System.out.println("Found entity: " + entity.getType().getRegistryName());
            if (!world.isClientSide && !(entity instanceof Player) && !entity.fireImmune()) {
                entity.setSecondsOnFire(5); // Adjust the burn duration as needed
                System.out.println("Entity set on fire: " + entity.getDisplayName().getString());
            }
        }

        world.playSound(null, centerPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 5.0f, 1.0f);
    }



    // Tooltip
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.maho.heatcloud_spell.tooltip"));
    }
}
