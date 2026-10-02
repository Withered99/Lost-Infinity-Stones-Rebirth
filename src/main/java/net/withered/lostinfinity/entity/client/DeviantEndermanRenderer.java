package net.withered.lostinfinity.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.withered.lostinfinity.entity.custom.DeviantEndermanEntity;

public class DeviantEndermanRenderer extends MobEntityRenderer<DeviantEndermanEntity, DeviantEndermanModel<DeviantEndermanEntity>>   {
    public DeviantEndermanRenderer(EntityRendererFactory.Context context) {
        super(context, new DeviantEndermanModel<>(context.getPart(DeviantEndermanModel.DEVIANT_ENDERMAN)), 0.75f);
    }

    @Override
    public Identifier getTexture(DeviantEndermanEntity entity) {
        return Identifier.of(LostInfinityStonesRebirth.MOD_ID, "textures/entity/deviant_enderman/deviant_enderman.png");
    }
}
