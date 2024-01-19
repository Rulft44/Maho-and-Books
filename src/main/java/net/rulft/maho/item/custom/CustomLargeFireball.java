package net.rulft.maho.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CustomLargeFireball extends LargeFireball {

    private final LivingEntity owner;
    private int age = 0;
    private static final int MAX_AGE = 100; // Adjust this value as needed
    private double speed = 1.5; // Adjust this value as needed

    public CustomLargeFireball(EntityType<? extends LargeFireball> entityType, Level level, LivingEntity owner, double x, double y, double z, double xPower, double yPower, double zPower) {
        super(entityType, level);
        this.owner = owner;

        this.setPos(x, y, z);
        this.setDeltaMovement(xPower, yPower, zPower);
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    @Override
    public void tick() {
        super.tick();

        // Increment the age of the fireball
        age++;

        // Check if the fireball has reached its maximum age
        if (age >= MAX_AGE) {
            explode();
        }

        // Adjust the motion to maintain speed
        Vec3 motion = this.getDeltaMovement().normalize().scale(speed);
        this.setDeltaMovement(motion.x, motion.y, motion.z);
    }

    private void explode() {
        // Implement explosion logic here, e.g., spawn particles, deal damage, etc.
        this.remove(Entity.RemovalReason.DISCARDED);
        boolean noPhysics1 = this.noPhysics;
        this.level.addParticle(ParticleTypes.EXPLOSION , this.getX(), this.getY(), this.getZ(), 1.0D, 1.0D, 1.0D);
    }
}
