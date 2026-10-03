package net.withered.lostinfinity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.item.ModItems;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CELESTIAL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTIAL_DIAMOND, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEVIANT_ENDER_PEARL, Models.GENERATED);
        itemModelGenerator.register(ModItems.PERFECT_PEARL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ELARA_NECKLACE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEVIANT_SHULKER_SHELL, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_DUALITY, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_DUALITY_CONTAINED, Models.GENERATED);
        itemModelGenerator.register(ModItems.SKYWORM_TOOTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNPOWERED_BLADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEVIANT_ENDERMAN_SPAWN_EGG ,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.DEVIANT_SHULKER_SPAWN_EGG ,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.DEVIANT_SKYWORM_SPAWN_EGG ,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
    }
}