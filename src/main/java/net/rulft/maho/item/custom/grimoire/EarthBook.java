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

public class EarthBook extends GrimoireItem {
    private String grimoireType;
    public EarthBook(String grimoireType, Properties pProperties) {
        super(pProperties);
        this.grimoireType = grimoireType;
    }

    public String getGrimoireType() {
        return grimoireType;
    }

    // Tooltip
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        // Add specific details based on the Grimoire type
        tooltip.add(new TextComponent("A special Grimoire for those with great ").withStyle(ChatFormatting.GRAY)
                .append(new TextComponent(getGrimoireType() + " Powers").withStyle(ChatFormatting.GOLD)));


        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}

