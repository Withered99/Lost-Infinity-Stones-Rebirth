package net.withered.lostinfinity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.entity.ModEntities;
import net.withered.lostinfinity.entity.client.*;
import net.withered.lostinfinity.item.ModItems;

public class LostInfinityStonesRebirthClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DeviantEndermanModel.DEVIANT_ENDERMAN, DeviantEndermanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DeviantSkywormModel.DEVIANT_SKYWORM, DeviantSkywormModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DEVIANT_ENDERMAN, DeviantEndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVIANT_SHULKER, DeviantShulkerRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVIANT_SKYWORM, DeviantSkywormRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVIANT_BEAR, DeviantBearRenderer::new);

        ModelPredicateProviderRegistry.register(
                ModItems.DUALITY_BLADES,
                Identifier.of("lostinfinity", "blade"),
                (stack, world, entity, seed) -> {
                    NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
                    return nbtComponent.copyNbt().getBoolean("blade") ? 1.0F : 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.CONVERGENCE_BLADES,
                Identifier.of("lostinfinity", "blade2"),
                (stack, world, entity, seed) -> {
                    NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
                    return nbtComponent.copyNbt().getBoolean("blade2") ? 1.0F : 0.0F;
                }
        );

        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                float currentHealth = client.player.getHealth();
                float maxHealth = client.player.getMaxHealth();

                String formattedCurrent = currentHealth >= 1000.0f
                        ? String.format("%.1fk", currentHealth / 1000.0f)
                        : String.format("%.1f", currentHealth);

                String formattedMax = maxHealth >= 1000.0f
                        ? String.format("%.1fk", maxHealth / 1000.0f)
                        : String.format("%.1f", maxHealth);

                String healthText = formattedCurrent + " | " + formattedMax;

                int color = 0xAA0000;
                boolean dropShadow = true;
                float scale = 1.5f;

                int screenWidth = drawContext.getScaledWindowWidth();
                int screenHeight = drawContext.getScaledWindowHeight();
                int hotbarLeftEdge = (screenWidth / 2) - 91;
                int textWidth = client.textRenderer.getWidth(healthText);
                float targetX = hotbarLeftEdge - 5 - (textWidth * scale);
                float targetY = screenHeight - 10 - (4 * scale);

                drawContext.getMatrices().push();
                drawContext.getMatrices().translate(targetX, targetY, 0);
                drawContext.getMatrices().scale(scale, scale, 1.0f);

                drawContext.drawText(
                        client.textRenderer,
                        healthText,
                        0,
                        0,
                        color,
                        dropShadow
                );

                drawContext.getMatrices().pop();
            }
        });
    }
}
