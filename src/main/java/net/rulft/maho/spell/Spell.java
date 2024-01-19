// Spell.java
package net.rulft.maho.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Spell {
    void cast(Player caster);
    void castWithEffect(Player caster, double effectMultiplier);
}
