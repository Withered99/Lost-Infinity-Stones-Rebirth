package net.withered.lostinfinity;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.withered.lostinfinity.blocks.ModBlocks;
import net.withered.lostinfinity.entity.ModEntities;
import net.withered.lostinfinity.entity.custom.DeviantBearEntity;
import net.withered.lostinfinity.entity.custom.DeviantEndermanEntity;
import net.withered.lostinfinity.entity.custom.DeviantShulkerEntity;
import net.withered.lostinfinity.entity.custom.DeviantSkywormEntity;
import net.withered.lostinfinity.event.ModEntitySpawns;
import net.withered.lostinfinity.item.ModItemGroups;
import net.withered.lostinfinity.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LostInfinityStonesRebirth implements ModInitializer {
	public static final String MOD_ID = "lostinfinity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerItemGroups();
        ModEntities.registerModEntities();
        ModEntitySpawns.registerSpawns();

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 4  , factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.CELESTIAL_DIAMOND, 5),
                    new ItemStack(ModItems.MAP_DUALITY, 1), 1, 2, 0.2f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 4  , factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.CELESTIAL_DIAMOND, 5),
                    new ItemStack(ModItems.MAP_ASPIRATION, 1), 1, 2, 0.2f
            ));
        });

        FabricDefaultAttributeRegistry.register(ModEntities.DEVIANT_ENDERMAN, DeviantEndermanEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DEVIANT_SHULKER, DeviantShulkerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DEVIANT_SKYWORM, DeviantSkywormEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DEVIANT_BEAR, DeviantBearEntity.createAttributes());
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
