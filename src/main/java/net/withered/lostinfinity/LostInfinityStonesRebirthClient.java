package net.withered.lostinfinity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.withered.lostinfinity.entity.ModEntities;
import net.withered.lostinfinity.entity.client.*;

public class LostInfinityStonesRebirthClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DeviantEndermanModel.DEVIANT_ENDERMAN, DeviantEndermanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DeviantSkywormModel.DEVIANT_SKYWORM, DeviantSkywormModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DEVIANT_ENDERMAN, DeviantEndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVIANT_SHULKER, DeviantShulkerRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVIANT_SKYWORM, DeviantSkywormRenderer::new);
    }
}
