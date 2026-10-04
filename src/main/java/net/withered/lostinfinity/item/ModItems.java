package net.withered.lostinfinity.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.entity.ModEntities;
import net.withered.lostinfinity.item.custom.CelestialPickaxeItem;
import net.withered.lostinfinity.item.custom.map.AspirationMapItem;
import net.withered.lostinfinity.item.custom.map.DualityMapItem;
import net.withered.lostinfinity.item.custom.stones.contained.AspirationStoneContainedItem;
import net.withered.lostinfinity.item.custom.stones.contained.DualityStoneContainedItem;
import net.withered.lostinfinity.item.custom.stones.natural.AspirationStoneItem;
import net.withered.lostinfinity.item.custom.stones.natural.DualityStoneItem;
import net.withered.lostinfinity.item.custom.weapons.ConvergenceBladesItem;
import net.withered.lostinfinity.item.custom.weapons.DualityBladesItem;
import net.withered.lostinfinity.item.custom.weapons.SkyvergeItem;

public class ModItems {
    public static final Item DEVIANT_ENDERMAN_SPAWN_EGG = registerItem("deviant_enderman_spawn_egg",
            new SpawnEggItem(ModEntities.DEVIANT_ENDERMAN, 0x9dc783, 0xbfaf5f, new Item.Settings()));
    public static final Item DEVIANT_SHULKER_SPAWN_EGG = registerItem("deviant_shulker_spawn_egg",
            new SpawnEggItem(ModEntities.DEVIANT_SHULKER, 0x9dc783, 0xbfaf5f, new Item.Settings()));
    public static final Item DEVIANT_SKYWORM_SPAWN_EGG = registerItem("deviant_skyworm_spawn_egg",
            new SpawnEggItem(ModEntities.DEVIANT_SHULKER, 0x9dc783, 0xbfaf5f, new Item.Settings()));
    public static final Item DEVIANT_BEAR_SPAWN_EGG = registerItem("deviant_bear_spawn_egg",
            new SpawnEggItem(ModEntities.DEVIANT_BEAR, 0x9dc783, 0xbfaf5f, new Item.Settings()));

    public static final Item MAP_DUALITY = registerItem("map_duality", new DualityMapItem(new Item.Settings()));
    public static final Item MAP_ASPIRATION = registerItem("map_aspiration", new AspirationMapItem(new Item.Settings()));

    public static final Item SKYWORM_TOOTH = registerItem("skyworm_tooth", new Item(new Item.Settings()));
    public static final Item DEVIANT_ENDER_PEARL = registerItem("deviant_ender_pearl", new Item(new Item.Settings()));
    public static final Item DEVIANT_BEAR_HIDE = registerItem("deviant_bear_hide", new Item(new Item.Settings()));
    public static final Item DEVIANT_SHULKER_SHELL = registerItem("deviant_shulker_shell", new Item(new Item.Settings()));
    public static final Item PERFECT_PEARL = registerItem("perfect_pearl", new Item(new Item.Settings()));

    public static final Item ELARA_NECKLACE = registerItem("elara_necklace", new Item(new Item.Settings()));
    public static final Item UNPOWERED_BLADE = registerItem("unpowered_blade", new Item(new Item.Settings()));

    public static final Item DUALITY_BLADES = registerItem("duality_blades", new DualityBladesItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 0, -2.4f))));
    public static final Item SKYVERGE = registerItem("skyverge", new SkyvergeItem(new Item.Settings().maxCount(1)));

    public static final Item STONE_DUALITY_CONTAINED = registerItem("stone_duality_contained", new DualityStoneContainedItem(new Item.Settings()));
    public static final Item STONE_ASPIRATION_CONTAINED = registerItem("stone_aspiration_contained", new AspirationStoneContainedItem(new Item.Settings()));

    public static final Item STONE_DUALITY = registerItem("stone_duality", new DualityStoneItem(new Item.Settings()));
    public static final Item STONE_ASPIRATION = registerItem("stone_aspiration", new AspirationStoneItem(new Item.Settings()));

    public static final Item CELESTIAL_PICKAXE = registerItem("celestial_pickaxe", new CelestialPickaxeItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 0, -2.4f))));
    public static final Item CELESTIAL_IRON = registerItem("celestial_iron", new Item(new Item.Settings()));
    public static final Item CELESTIAL_DIAMOND = registerItem("celestial_diamond", new Item(new Item.Settings()));

    //WIP
    public static final Item CONVERGENCE_BLADES = registerItem("convergence_blades", new ConvergenceBladesItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 0, -2.4f))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LostInfinityStonesRebirth.MOD_ID, name), item);
    }

    private static void customIngredients(FabricItemGroupEntries entries) {
    }

    public static void registerModItems() {
        LostInfinityStonesRebirth.LOGGER.info("Registering Mod Items for " + LostInfinityStonesRebirth.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customIngredients);
    }
}