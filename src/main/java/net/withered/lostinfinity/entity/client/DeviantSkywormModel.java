package net.withered.lostinfinity.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.withered.lostinfinity.entity.custom.DeviantSkywormEntity;

public class DeviantSkywormModel<T extends DeviantSkywormEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer DEVIANT_SKYWORM = new EntityModelLayer(Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_skyworm"), "main");

    private final ModelPart skyworm;
    private final ModelPart body;
    private final ModelPart seg_back;
    private final ModelPart seg_mid;
    private final ModelPart seg_front;
    private final ModelPart headwear;
    private final ModelPart tail;
    private final ModelPart tail2;
    public DeviantSkywormModel(ModelPart root) {
        this.skyworm = root.getChild("skyworm");
        this.body = this.skyworm.getChild("body");
        this.seg_back = this.skyworm.getChild("seg_back");
        this.seg_mid = this.skyworm.getChild("seg_mid");
        this.seg_front = this.skyworm.getChild("seg_front");
        this.headwear = this.skyworm.getChild("headwear");
        this.tail = this.skyworm.getChild("tail");
        this.tail2 = this.skyworm.getChild("tail2");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData skyworm = modelPartData.addChild("skyworm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, -5.0F));

        ModelPartData body = skyworm.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -14.0F, -3.0F, 12.0F, 12.0F, 91.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData seg_back = skyworm.addChild("seg_back", ModelPartBuilder.create().uv(120, 103).cuboid(-8.0F, -16.0F, 73.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData seg_mid = skyworm.addChild("seg_mid", ModelPartBuilder.create().uv(0, 135).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 103).cuboid(-22.0F, 0.0F, -8.0F, 44.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 49.0F));

        ModelPartData seg_front = skyworm.addChild("seg_front", ModelPartBuilder.create().uv(64, 135).cuboid(-8.0F, -8.0F, -7.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 119).cuboid(-22.0F, 0.0F, -7.0F, 44.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 16.0F));

        ModelPartData headwear = skyworm.addChild("headwear", ModelPartBuilder.create().uv(128, 151).cuboid(2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(136, 151).cuboid(-4.0F, -2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tail = skyworm.addChild("tail", ModelPartBuilder.create().uv(128, 135).cuboid(-7.0F, 0.0F, 0.0F, 14.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 89.0F));

        ModelPartData tail2 = skyworm.addChild("tail2", ModelPartBuilder.create().uv(128, 135).cuboid(-7.0F, 0.0F, 0.0F, 14.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 89.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }
    @Override
    public void setAngles(DeviantSkywormEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.idleAnimationState, DeviantSkywormAnimations.IDLE, ageInTicks, 1f);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        skyworm.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return skyworm;
    }
}
