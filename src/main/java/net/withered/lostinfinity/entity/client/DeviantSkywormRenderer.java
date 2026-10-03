package net.withered.lostinfinity.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.withered.lostinfinity.entity.custom.DeviantSkywormEntity;

public class DeviantSkywormRenderer extends MobEntityRenderer<DeviantSkywormEntity, DeviantSkywormModel<DeviantSkywormEntity>>   {
    public DeviantSkywormRenderer(EntityRendererFactory.Context context) {
        super(context, new DeviantSkywormModel<>(context.getPart(DeviantSkywormModel.DEVIANT_SKYWORM)), 0.75f);
    }

    @Override
    public Identifier getTexture(DeviantSkywormEntity entity) {
        return Identifier.of(LostInfinityStonesRebirth.MOD_ID, "textures/entity/deviant_skyworm/deviant_skyworm.png");
    }
}
