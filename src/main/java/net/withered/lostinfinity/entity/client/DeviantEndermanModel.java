package net.withered.lostinfinity.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.withered.lostinfinity.entity.custom.DeviantEndermanEntity;

public class DeviantEndermanModel<T extends DeviantEndermanEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer DEVIANT_ENDERMAN = new EntityModelLayer(Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_enderman"), "main");

    private final ModelPart deviant_enderman;
    private final ModelPart left_leg;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart headwear;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart right_leg;

    public DeviantEndermanModel(ModelPart root) {
        this.deviant_enderman = root.getChild("deviant_enderman");
        this.left_leg = this.deviant_enderman.getChild("left_leg");
        this.body = this.deviant_enderman.getChild("body");
        this.head = this.deviant_enderman.getChild("head");
        this.headwear = this.deviant_enderman.getChild("headwear");
        this.right_arm = this.deviant_enderman.getChild("right_arm");
        this.left_arm = this.deviant_enderman.getChild("left_arm");
        this.right_leg = this.deviant_enderman.getChild("right_leg");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData deviant_enderman = modelPartData.addChild("deviant_enderman", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData left_leg = deviant_enderman.addChild("left_leg", ModelPartBuilder.create().uv(56, 0).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, -30.0F, 0.0F));

        ModelPartData body = deviant_enderman.addChild("body", ModelPartBuilder.create().uv(32, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -39.0F, 0.0F));

        ModelPartData head = deviant_enderman.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -39.0F, 0.0F));

        ModelPartData headwear = deviant_enderman.addChild("headwear", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, -39.0F, 0.0F));

        ModelPartData right_arm = deviant_enderman.addChild("right_arm", ModelPartBuilder.create().uv(56, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -37.0F, 0.0F));

        ModelPartData left_arm = deviant_enderman.addChild("left_arm", ModelPartBuilder.create().uv(56, 0).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, -37.0F, 0.0F));

        ModelPartData right_leg = deviant_enderman.addChild("right_leg", ModelPartBuilder.create().uv(56, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -30.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }
    @Override
    public void setAngles(DeviantEndermanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(DeviantEndermanAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, DeviantEndermanAnimations.IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, DeviantEndermanAnimations.ATTACK, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        deviant_enderman.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return deviant_enderman;
    }
}
