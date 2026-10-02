package net.withered.lostinfinity.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;

public class DeviantShulkerEntityRenderer extends ShulkerEntityRenderer {
    public DeviantShulkerEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    private static final Identifier TEXTURE = Identifier.of("lostinfinity", "textures/entity/deviant_shulker/deviant_shulker.png");

    @Override
    public Identifier getTexture(ShulkerEntity entity) {
        return TEXTURE;
    }
}
