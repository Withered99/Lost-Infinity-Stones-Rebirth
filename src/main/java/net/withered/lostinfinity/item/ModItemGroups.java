package net.withered.lostinfinity.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Items;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup LOST_INFINITY_STONES_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(LostInfinityStonesRebirth.MOD_ID, "lost_infinity_stones"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.lost_infinity_stones"))
                    .icon(() -> new ItemStack(ModItems.CELESTIAL_PICKAXE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CELESTIAL_PICKAXE);
                        entries.add(ModItems.CELESTIAL_DIAMOND);
                        entries.add(ModItems.DEVIANT_SHULKER_SHELL);
                        entries.add(ModItems.DEVIANT_ENDER_PEARL);
                        entries.add(ModItems.DEVIANT_ENDERMAN_SPAWN_EGG);
                        entries.add(ModItems.DEVIANT_SHULKER_SPAWN_EGG);
                        entries.add(ModItems.DEVIANT_SKYWORM_SPAWN_EGG);
                        entries.add(ModItems.PERFECT_PEARL);
                        entries.add(ModItems.ELARA_NECKLACE);
                        entries.add(ModItems.SKYWORM_TOOTH);
                        entries.add(ModItems.UNPOWERED_BLADE);
                        entries.add(ModItems.MAP_DUALITY);
                        entries.add(ModItems.STONE_DUALITY);
                        entries.add(ModItems.STONE_DUALITY_CONTAINED);
                        entries.add(ModItems.DUALITY_BLADES);
                    }).build());
    public static void registerItemGroups() {
        LostInfinityStonesRebirth.LOGGER.info("Registering Item Groups for " + LostInfinityStonesRebirth.MOD_ID);
    }
}