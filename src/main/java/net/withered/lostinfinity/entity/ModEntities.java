package net.withered.lostinfinity.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.withered.lostinfinity.LostInfinityStonesRebirth;
import net.withered.lostinfinity.entity.custom.DeviantEndermanEntity;
import net.withered.lostinfinity.entity.custom.DeviantShulkerEntity;
import net.withered.lostinfinity.entity.custom.DeviantSkywormEntity;

public class ModEntities {

    public static final EntityType<DeviantEndermanEntity> DEVIANT_ENDERMAN = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_enderman"),
            EntityType.Builder.create(DeviantEndermanEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 3f).build());

    public static final EntityType<DeviantShulkerEntity> DEVIANT_SHULKER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_shulker"),
            EntityType.Builder.create(DeviantShulkerEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 1f).build());

    public static final EntityType<DeviantSkywormEntity> DEVIANT_SKYWORM = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LostInfinityStonesRebirth.MOD_ID, "deviant_skyworm"),
            EntityType.Builder.create(DeviantSkywormEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 1f).build());

    public  static void registerModEntities() {
        SpawnRestriction.register(
                DEVIANT_SKYWORM,
                SpawnLocationTypes.UNRESTRICTED,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (type, world, spawnReason, pos, random) ->
                        world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL
                                && MobEntity.canMobSpawn(type, world, spawnReason, pos, random)
        );
    }
}
