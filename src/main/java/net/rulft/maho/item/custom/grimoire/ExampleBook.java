package net.rulft.maho.item.custom.grimoire;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExampleBook extends Item {
    private String grimoireType = "generic";

    public ExampleBook(String grimoireType, Item.Properties pProperties) {
        super(pProperties);
        this.grimoireType = "generic";
    }

    public String getGrimoireType() {
        return grimoireType;
    }

    // Tooltip
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        // Add specific details based on the Grimoire type
        tooltip.add(new TextComponent("A special Grimoire for those with great ").withStyle(ChatFormatting.RED)
                .append(new TextComponent("Generic Powers").withStyle(ChatFormatting.DARK_RED)));


        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}

