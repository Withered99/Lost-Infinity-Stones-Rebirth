package net.withered.lostinfinity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.withered.lostinfinity.entity.ModEntities;
import net.withered.lostinfinity.entity.client.DeviantEndermanModel;
import net.withered.lostinfinity.entity.client.DeviantEndermanRenderer;
import net.withered.lostinfinity.entity.client.DeviantShulkerEntityRenderer;
import net.withered.lostinfinity.item.ModItemGroups;
import net.withered.lostinfinity.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LostInfinityStonesRebirthClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DeviantEndermanModel.DEVIANT_ENDERMAN, DeviantEndermanModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.DEVIANT_ENDERMAN, DeviantEndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVIANT_SHULKER, DeviantShulkerEntityRenderer::new);
    }
}
