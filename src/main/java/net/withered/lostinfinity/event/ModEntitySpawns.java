package net.withered.lostinfinity.event;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.BiomeKeys;
import net.withered.lostinfinity.entity.ModEntities;
import net.withered.lostinfinity.entity.custom.DeviantEndermanEntity;

public class ModEntitySpawns {

    public static void registerSpawns() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity.getWorld() instanceof ServerWorld serverWorld) {
                if (entity instanceof EndermanEntity && !(entity instanceof DeviantEndermanEntity)) {
                    trySpawnDeviant(serverWorld, entity, ModEntities.DEVIANT_ENDERMAN, 0.05F);
                }
            }
        });

        //BiomeModifications.addSpawn(
        //        BiomeSelectors.includeByKey(
        //                BiomeKeys.WINDSWEPT_HILLS,
        //                BiomeKeys.WINDSWEPT_FOREST,
        //                BiomeKeys.WINDSWEPT_GRAVELLY_HILLS
        //        ),
        //        SpawnGroup.MONSTER,
        //        ModEntities.DEVIANT_SKYWORM,
        //        2,
        //        1,
        //        1
        //);
    }

    @SuppressWarnings("rawtypes")
    private static void trySpawnDeviant(ServerWorld world, LivingEntity deadEntity, EntityType typeToSpawn, float chance) {
        if (world.getRandom().nextFloat() < chance) {
            LivingEntity spawnedEntity = (LivingEntity) typeToSpawn.create(
                    world,
                    null,
                    deadEntity.getBlockPos(),
                    SpawnReason.TRIGGERED,
                    false,
                    false
            );

            if (spawnedEntity != null) {
                spawnedEntity.refreshPositionAndAngles(
                        deadEntity.getX(),
                        deadEntity.getY(),
                        deadEntity.getZ(),
                        deadEntity.getYaw(),
                        deadEntity.getPitch()
                );
                world.spawnEntity(spawnedEntity);
            }
        }
    }
}