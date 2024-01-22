package net.rulft.maho.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.rulft.maho.item.ModItems;
import net.rulft.maho.item.custom.grimoire.GrimoireItem;
import net.rulft.maho.spell.SpellItem;

import java.util.List;

public class StrangeScrollItem extends Item {
    public StrangeScrollItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        System.out.println("StrangeScrollItem use method called"); // Check if the method is being called
        ItemStack itemStack = player.getItemInHand(hand);

        // Check if the player is holding a grimoire
        ItemStack grimoireStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (grimoireStack.getItem() instanceof GrimoireItem) {
            System.out.println("Grimoire detected: " + ((GrimoireItem) grimoireStack.getItem()).getGrimoireType());
            // Get the grimoire type
            String grimoireType = ((GrimoireItem) grimoireStack.getItem()).getGrimoireType();

            // Get a random spell of the same type
            Item randomSpell = getRandomSpellOfType(grimoireType, player.level);

            // Give the player the random spell
            if (randomSpell != null) {
                ItemStack spellStack = new ItemStack(randomSpell);
                player.getInventory().add(spellStack);

                // Consume the scroll
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
            }
        }

        // If not holding a grimoire or no suitable spell found, return a fail result
        return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
    }

    private Item getRandomSpellOfType(String spellType, Level level) {
        System.out.println("Searching for spell of type: " + spellType);
        // Add your spell items to this array
        Item[] spells = {ModItems.FIREBALL_SPELL.get(),
                ModItems.HEATCLOUD_SPELL.get()}; // Add more as needed

        // Filter spells based on the requested type
        Item[] filteredSpells = java.util.Arrays.stream(spells)
                .filter(spell -> ((SpellItem) spell).getSpellType().equals(spellType))
                .toArray(Item[]::new);

        // Get a random spell from the filtered array
        return (filteredSpells.length > 0) ? filteredSpells[level.random.nextInt(filteredSpells.length)] : null;

    }


    // Tooltip
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip.maho.strange_scroll.tooltip"));
    }
}
