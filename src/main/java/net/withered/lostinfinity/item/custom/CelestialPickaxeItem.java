package net.withered.lostinfinity.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.withered.lostinfinity.item.ModItems;

import java.util.List;

public class CelestialPickaxeItem extends PickaxeItem {
    public CelestialPickaxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && (state.isOf(Blocks.DIAMOND_ORE) || state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE))) {
            if (world.random.nextFloat() < 0.05f) {
                ItemEntity stringEntity = new ItemEntity(
                        world,
                        pos.getX() + 0.5D,
                        pos.getY() + 0.5D,
                        pos.getZ() + 0.5D,
                        new ItemStack(ModItems.CELESTIAL_DIAMOND)
                );
                world.spawnEntity(stringEntity);
            }

        }
        if (!world.isClient() && (state.isOf(Blocks.IRON_ORE) || state.isOf(Blocks.DEEPSLATE_IRON_ORE))) {
            if (world.random.nextFloat() < 0.05f) {
                ItemEntity stringEntity = new ItemEntity(
                        world,
                        pos.getX() + 0.5D,
                        pos.getY() + 0.5D,
                        pos.getZ() + 0.5D,
                        new ItemStack(ModItems.CELESTIAL_IRON)
                );
                world.spawnEntity(stringEntity);
            }

        }
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List tooltip, TooltipType type) {
        tooltip.add(Text.literal("Searches alternate timelines for celestial gems when mining ores.").formatted(Formatting.LIGHT_PURPLE));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
