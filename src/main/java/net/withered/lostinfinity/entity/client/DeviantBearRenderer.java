package net.withered.lostinfinity.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PolarBearEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;

public class DeviantBearRenderer extends PolarBearEntityRenderer {
    public DeviantBearRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    private static final Identifier TEXTURE = Identifier.of("lostinfinity", "textures/entity/deviant_bear/deviant_bear.png");

    @Override
    public Identifier getTexture(PolarBearEntity entity) {
        return TEXTURE;
    }
}
