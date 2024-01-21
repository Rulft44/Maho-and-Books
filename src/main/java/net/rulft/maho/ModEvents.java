package net.rulft.maho;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.rulft.maho.item.ModItems;
import net.rulft.maho.item.custom.grimoire.FireBook;
import net.rulft.maho.item.custom.grimoire.GrimoireItem;

import static net.rulft.maho.MahoMod.MOD_ID;

// Event handler class
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final String GRIMOIRE_RECEIVED_KEY = "maho:grimoireReceived";

    // Event handler for player join event
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();

        // Check if the player is not a fake player (like command blocks or other automation)
        if (!(player instanceof FakePlayer)) {
            // Check if the player has not received a Grimoire yet
            if (!hasReceivedGrimoire(player)) {
                // Give the player a random Grimoire
                giveRandomGrimoire(player);

                // Mark the player as having received a Grimoire
                setReceivedGrimoire(player);
            }
        }
    }



    // Method to give a random Grimoire to the player
    private static void giveRandomGrimoire(Player player) {
        // Add your Grimoire items to this array
        Item[] grimoires = {ModItems.FIRE_BOOK.get(),
                            ModItems.EARTH_BOOK.get()};

        // Get a random Grimoire from the array
        Item randomGrimoire = grimoires[player.getRandom().nextInt(grimoires.length)];

        // Determine the Grimoire type based on the item
        String grimoireType = "Generic";  // Default value
        if (randomGrimoire instanceof GrimoireItem) {
            grimoireType = ((GrimoireItem) randomGrimoire).getGrimoireType();
        }


        // Create the formatted Grimoire name with no formatting for the player's name
        String playerName = player.getDisplayName().getString();
        String grimoireName = String.format("%s's %s Grimoire", playerName, grimoireType);

        // Give the player the random Grimoire with the formatted name
        ItemStack grimoireStack = new ItemStack(randomGrimoire);

        // Set the KeepOnDeath attribute to true
        grimoireStack.getOrCreateTag().putBoolean("KeepOnDeath", true);

        // Set the display name separately to avoid formatting issues
        grimoireStack.setHoverName(new TextComponent(grimoireName));

        // Give the Grimoire to the player
        player.getInventory().add(grimoireStack);
    }

    // Method to check if the player has received a Grimoire
    private static boolean hasReceivedGrimoire(Player player) {
        return player.getPersistentData().getBoolean(GRIMOIRE_RECEIVED_KEY);
    }

    // Method to mark the player as having received a Grimoire
    private static void setReceivedGrimoire(Player player) {
        player.getPersistentData().putBoolean(GRIMOIRE_RECEIVED_KEY, true);
    }


}

