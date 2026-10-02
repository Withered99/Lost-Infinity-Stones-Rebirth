package net.withered.lostinfinity.item.custom.map;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.withered.lostinfinity.item.ModItems;

import java.util.List;

public class DualityMapItem extends Item {
    public DualityMapItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List tooltip, TooltipType type) {
        tooltip.add(Text.literal("A map to a world within our world, that remains unexplored. In the center, Elara's Necklace is seen.").formatted(Formatting.GOLD));
        tooltip.add(Text.literal("You will challenge Elara, the Siren").formatted(Formatting.RED));

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public TypedActionResult use(World world, PlayerEntity user, Hand hand) {
        RegistryEntry biomeEntry = user.getWorld().getBiome(user.getBlockPos());
        ItemStack itemStack = user.getStackInHand(hand);

        if (biomeEntry.matchesKey(BiomeKeys.OCEAN)) {
            if (!world.isClient()) {
                boolean hasNecklace = false;
                boolean hasMap = false;
                ItemStack necklaceStack = ItemStack.EMPTY;
                ItemStack mapStack = ItemStack.EMPTY;

                for (ItemStack invStack : user.getInventory().main) {
                    if (!hasNecklace && invStack.isOf(ModItems.ELARA_NECKLACE)) {
                        necklaceStack = invStack;
                        hasNecklace = true;
                    }
                    if (!hasMap && invStack.isOf(ModItems.MAP_DUALITY)) {
                        mapStack = invStack;
                        hasMap = true;
                    }
                    if (hasNecklace && hasMap) {
                        break;
                    }
                }

                if (hasNecklace && hasMap) {
                    necklaceStack.decrement(1);
                    mapStack.decrement(1);

                    user.dropStack(new ItemStack(ModItems.STONE_DUALITY, 1));
                    user.sendMessage(Text.literal("Bosses are a work in progress here have the stone").formatted(Formatting.AQUA), false);
                } else {
                    user.sendMessage(Text.literal("Missing spawn item").formatted(Formatting.DARK_RED), false);
                }
            }
            return TypedActionResult.pass(itemStack);
        } else {
            if (!world.isClient()) {
                user.sendMessage(Text.literal("This biome does not match the clue.").formatted(Formatting.DARK_RED), false);
            }
            return TypedActionResult.pass(itemStack);
        }
    }
}