package net.withered.lostinfinity.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.withered.lostinfinity.entity.custom.DeviantEndermanEntity;
import net.withered.lostinfinity.entity.custom.DeviantShulkerEntity;

public class ModEntities {

    public static final EntityType<DeviantEndermanEntity> DEVIANT_ENDERMAN = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_enderman"),
            EntityType.Builder.create(DeviantEndermanEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 3f).build());

    public static final EntityType<DeviantShulkerEntity> DEVIANT_SHULKER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_shulker"),
            EntityType.Builder.create(DeviantShulkerEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 1f).build());

    public  static void registerModEntities() {
    }
}
