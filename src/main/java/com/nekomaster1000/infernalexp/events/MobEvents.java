package com.nekomaster1000.infernalexp.events;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.MobInteractions;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.MobSpawning;
import com.nekomaster1000.infernalexp.data.SpawnrateManager;
import com.nekomaster1000.infernalexp.entities.BasaltGiantEntity;
import com.nekomaster1000.infernalexp.entities.EmbodyEntity;
import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;
import com.nekomaster1000.infernalexp.entities.VolineEntity;
import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;
import com.nekomaster1000.infernalexp.entities.ai.AvoidBlockGoal;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event) {

		//
		//RUN AWAY!!
		//

		//Piglins fear Warpbeetles and Embodies
		if (event.getEntity() instanceof PiglinEntity){
			if (MobInteractions.PIGLIN_FEAR_WARPBEETLE.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
						new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
								WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
			}
			if (MobInteractions.PIGLIN_FEAR_EMBODY.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
						new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
								EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
			}
		}

		if (event.getEntity() instanceof HoglinEntity){
			if (MobInteractions.HOGLIN_FEAR_WARPBEETLE.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
						new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
								WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
			}
			if (MobInteractions.HOGLIN_FEAR_EMBODY.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
						new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
								EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
			}
		}

		//
		//ATTACK!!
		//

		//Spiders attack Warp beetles
		if (event.getEntity() instanceof SpiderEntity && MobInteractions.SPIDER_ATTACK_WARPBEETLE.getBoolean()) {
			((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
					new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
							WarpbeetleEntity.class, true, false));

		}


		//Skeletons attacks Piglins, Brutes, Embodies & Basalt Giants
		if (event.getEntity() instanceof SkeletonEntity) {
			if (MobInteractions.SKELETON_ATTACK_PIGLIN.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								PiglinEntity.class, true, false));
			}
			if (MobInteractions.SKELETON_ATTACK_BRUTE.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								PiglinBruteEntity.class, true, false));
			}
			if (MobInteractions.SKELETON_ATTACK_EMBODY.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(3,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								EmbodyEntity.class, true, false));
			}
			if (MobInteractions.SKELETON_ATTACK_GIANT.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								BasaltGiantEntity.class, true, false));
			}
		}

		//Piglins attack Skeletons & Voline
		if (event.getEntity() instanceof PiglinEntity) {
			if (MobInteractions.PIGLIN_ATTACK_SKELETON.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								SkeletonEntity.class, true, false));
			}
			if (MobInteractions.PIGLIN_ATTACK_VOLINE.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								VolineEntity.class, true, false));
			}
		}

		if (event.getEntity() instanceof PiglinBruteEntity){
			if (MobInteractions.BRUTE_ATTACK_SKELETON.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								SkeletonEntity.class, true, false));
			}
			if (MobInteractions.BRUTE_ATTACK_VOLINE.getBoolean()) {
				((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
								VolineEntity.class, true, false));
			}
		}


		//Ghasts attack Voline, Embodies, Skeletons
		if (event.getEntity() instanceof GhastEntity) {
			((FlyingEntity) event.getEntity()).targetSelector.addGoal(4,
					new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
							GlowsquitoEntity.class, true, false));

			if (MobInteractions.GHAST_ATTACK_EMBODY.getBoolean()) {
				((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
						new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
								EmbodyEntity.class, true, false));
			}
			if (MobInteractions.GHAST_ATTACK_VOLINE.getBoolean()) {
				((FlyingEntity) event.getEntity()).targetSelector.addGoal(2,
						new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
								VolineEntity.class, true, false));
			}
			if (MobInteractions.GHAST_ATTACK_SKELETON.getBoolean()) {
				((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
						new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
								SkeletonEntity.class, true, false));
			}
		}

		if (event.getEntity() instanceof MagmaCubeEntity) {

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_TORCH.get(),
                    16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_TORCH_WALL.get(),
                    16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_LANTERN.get(),
                    16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_CAMPFIRE.get(),
                    16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_FIRE.get(),
                    16.0F));
        }

    }

    private void addEntityToSpawner(BiomeLoadingEvent event, EntityType<?> entityType, SpawnrateManager.SpawnInfo spawnInfo) {
        // Check if creatureSpawnProbability is within the exclusive bounds of 0-1,
        // If so change the creatureSpawnProbability
        if (spawnInfo.getCreatureSpawnProbability() > 0 && spawnInfo.getCreatureSpawnProbability() < 1) {
            event.getSpawns().withCreatureSpawnProbability(spawnInfo.getCreatureSpawnProbability());
        }

        // Check if there are any other changes to be made to the spawner
        Optional.ofNullable(spawnInfo.getChanges()).ifPresent(changes -> changes.forEach((changeEntity, changeSpawnInfo) -> {
            // If so, get the entity type of the change
            EntityType<?> changeEntityType = ForgeRegistries.ENTITIES.getValue(changeEntity);

            // Check if the change entity type exists
            if (changeEntityType == null) {
                throw new ResourceLocationException("Invalid EntityType resource location " + entityType.toString());
            }

            // Check if the change entity type exists in the spawner
            MobSpawnInfo.Spawners spawner = event.getSpawns().getSpawner(changeEntityType.getClassification()).stream().filter(s -> s.type == changeEntityType).findFirst().orElseThrow(
                // If not, through an exception
                () -> new RuntimeException("Can't make changes to " + changeEntity.toString() + " because it doesn't exist in the spawner")
            );

            // Find which data to update from SpawnrateManager
            int spawnrate = changeSpawnInfo.getSpawnRate() != 0 ? changeSpawnInfo.getSpawnRate() : spawner.itemWeight;
            int minCount = changeSpawnInfo.getMinCount() != 0 ? changeSpawnInfo.getMinCount() : spawner.minCount;
            int maxCount = changeSpawnInfo.getMaxCount() != 0 ? changeSpawnInfo.getMaxCount() : spawner.maxCount;

            // Remove entity from spawner to make changes
            event.getSpawns().getSpawner(changeEntityType.getClassification()).remove(spawner);

            // Add it back with changes
            event.getSpawns().withSpawner(changeEntityType.getClassification(),
                new MobSpawnInfo.Spawners(changeEntityType, spawnrate, minCount, maxCount));
        }));

        // Add our entity to the spawner
        event.getSpawns().withSpawner(entityType.getClassification(),
            new MobSpawnInfo.Spawners(entityType, spawnInfo.getSpawnRate(), spawnInfo.getMinCount(), spawnInfo.getMaxCount()));
    }

    //Mob Spawning in pre-existing biomes
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getCategory() != Biome.Category.NETHER) {
            return;
        }

        MiscEvents.getSpawnrateManager().forEach((entity, value) -> {
            // Get the biomes the entity is allowed to spawn in from InfernalExpansionConfig
            List<String> spawnableBiomes = Arrays.asList(InfernalExpansionConfig.NewMobSpawning.getByName(entity.split(":")[1]).getSpawnableBiomes().replace(" ", "").split(","));

            // Check if the current biome getting loaded is in the spawnable biomes, if not, return
            if (!spawnableBiomes.contains(event.getName().toString())) {
                return;
            }

            // Get the entity type from the name
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity));

            // Check if the entity type exists
            if (entityType == null) {
                throw new ResourceLocationException("Invalid EntityType resource location " + entity);
            }

            // Find either the default spawn info or the spawn info for a specific biome from SpawnrateManager
            for (String spawnableBiome : spawnableBiomes) {
                if (value.containsKey(spawnableBiome)) {
                    addEntityToSpawner(event, entityType, value.get(spawnableBiome));
                } else if (value.containsKey("default")) {
                    addEntityToSpawner(event, entityType, value.get("default"));
                } else {
                    InfernalExpansion.LOGGER.error(entity + " doesn't have a default spawn info entry");
                }
            }
        });

        if (event.getName().toString().equals("minecraft:nether_wastes")) {
            if (MobSpawning.VOLINE_WASTES.isEnabled()) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.VOLINE.get(),
                        MobSpawning.VOLINE_WASTES.getSpawnrate(), 1, 3));
            }

        } else if (event.getName().toString().equals("minecraft:crimson_forest")) {
            if (MobSpawning.SHROOMLOIN_CRIMSON.isEnabled()) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.SHROOMLOIN.get(),
                        MobSpawning.SHROOMLOIN_CRIMSON.getSpawnrate(), 1, 3));
            }

            if (MobSpawning.VOLINE_CRIMSON.isEnabled()) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.VOLINE.get(),
                        MobSpawning.VOLINE_CRIMSON.getSpawnrate(), 1, 5));
            }

            if (MobSpawning.GLOWSILK_CRIMSON.isEnabled()) {
                event.getSpawns().withSpawner(EntityClassification.AMBIENT,
                    new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSILK_MOTH.get(),
                        MobSpawning.GLOWSILK_CRIMSON.getSpawnrate(), 1, 1));
            }

        } else if (event.getName().toString().equals("minecraft:warped_forest")) {

			//    event.getSpawns().withSpawner(EntityClassification.MONSTER,
			//            new MobSpawnInfo.Spawners(ModEntityType.CEROBEETLE.get(), 1, 1, 1));

			if (MobSpawning.WARPBEETLE_WARPED.isEnabled()) {
                // I think this creatureSpawnProbability is a ratio between how many creatures will spawn compared to other types, or maybe just monsters?
//                event.getSpawns().withCreatureSpawnProbability(0.9F);

                // Remove the strider spawner then add it back with a weight of 20 instead of 60
//                event.getSpawns().getSpawner(EntityClassification.CREATURE).removeIf(spawner -> spawner.type == EntityType.STRIDER);
//                event.getSpawns().withSpawner(EntityClassification.CREATURE,
//                    new MobSpawnInfo.Spawners(EntityType.STRIDER,
//                        20, 1, 2));
//
//                event.getSpawns().withSpawner(EntityClassification.CREATURE,
//                    new MobSpawnInfo.Spawners(IEEntityTypes.WARPBEETLE.get(),
//                        MobSpawning.WARPBEETLE_WARPED.getSpawnrate(), 1, 5));
            }

		} else if (event.getName().toString().equals("minecraft:basalt_deltas")) {
			if (MobSpawning.GIANT_DELTAS.isEnabled()) {
				event.getSpawns().withSpawner(EntityClassification.MONSTER,
						new MobSpawnInfo.Spawners(IEEntityTypes.BASALT_GIANT.get(),
								MobSpawning.GIANT_DELTAS.getSpawnrate(), 1, 1));
			}

			if (MobSpawning.GLOWSILK_DELTAS.isEnabled()) {
				event.getSpawns().withSpawner(EntityClassification.AMBIENT,
						new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSILK_MOTH.get(),
								MobSpawning.GLOWSILK_DELTAS.getSpawnrate(), 1, 1));
			}

			//event.getSpawns().withSpawner(EntityClassification.MONSTER,
			//        new MobSpawnInfo.Spawners(ModEntityType.GLOWSQUITO.get(), 1, 5, 10));


		} else if (event.getName().toString().equals("minecraft:soul_sand_valley")) {
			if (MobSpawning.EMBODY_SSV.isEnabled()) {
				event.getSpawns().withSpawner(EntityClassification.MONSTER,
						new MobSpawnInfo.Spawners(IEEntityTypes.EMBODY.get(),
								MobSpawning.EMBODY_SSV.getSpawnrate(), 1, 5));
			}

			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.SKELETAL_PIGLIN.get(), 10, 1, 1));


			//Mob Spawning in new biomes

		} else if (event.getName().toString().equals("infernalexp:glowstone_canyon")) {
			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSQUITO.get(), 80, 1, 10));

			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.BLINDSIGHT.get(), 10, 1, 1));

			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.SKELETAL_PIGLIN.get(), 10, 1, 1));

			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.BLACKSTONE_DWARF.get(), 1, 1, 1));

			if (MobSpawning.GLOWSILK_GSC.isEnabled()) {
				event.getSpawns().withSpawner(EntityClassification.AMBIENT,
						new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSILK_MOTH.get(),
								MobSpawning.GLOWSILK_GSC.getSpawnrate(), 1, 1));
			}

			//event.getSpawns().withSpawner(EntityClassification.MONSTER,
			//        new MobSpawnInfo.Spawners(EntityType.GHAST, 20, 1, 1));
			// Not spawning for some reason?

		} else if (event.getName().toString().equals("infernalexp:delta_shores")) {
			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.BASALT_GIANT.get(), 4, 1, 1));

			event.getSpawns().withSpawner(EntityClassification.MONSTER,
					new MobSpawnInfo.Spawners(IEEntityTypes.SKELETAL_PIGLIN.get(), 6, 1, 1));

		}
	}

}
