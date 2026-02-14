package com.hqw.minecraft.test01;

import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.mixin.screenhandler.ServerPlayerEntityMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Hqwtest01 implements ModInitializer {
	public static final String MOD_ID = "hqwtest01";
    private static final Random RANDOM = new Random();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
        ModItems.initialize();
        Sword.initialize();
        HQWArmor.initialize();

        AttackEntityCallback.EVENT.register((player, world, hand, attackedEntity, hitResult) -> {
            // 检查是否为主手攻击、服务端、且手持巨剑
            ItemStack mainHandStack = player.getMainHandItem();
            if (!world.isClientSide()
                    && player instanceof ServerPlayer serverPlayer
                    && hand == InteractionHand.MAIN_HAND
                    && mainHandStack.is(Sword.jjjsword)) {

                // 召唤以玩家为中心的雷暴（半径10格，3道闪电）
                summonLightningStorm(serverPlayer, world, 50.0F, 50);
            }
            return InteractionResult.PASS;
        });
	}
    private static void summonLightningStorm(ServerPlayer player, Level world, float radius, int count) {
        Position center = player.position();

        for (int i = 0; i < count; i++) {
            // 随机角度和距离
            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            double dist = RANDOM.nextDouble() * radius;
            double x = center.x() + Math.cos(angle) * dist;
            double z = center.z() + Math.sin(angle) * dist;

            // 获取地表高度（避免在虚空或岩浆海劈雷）
            BlockPos groundPos = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(x, center.y(), z));
            if (groundPos.getY() <= world.getMinY()) {
                groundPos = BlockPos.containing(x, center.y(), z);
            }

            // 创建并生成闪电
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(world, EntitySpawnReason.EVENT);
            if (lightning != null) {
                lightning.move(MoverType.SELF, Vec3.atCenterOf(groundPos));
                world.addFreshEntity(lightning);
            }
        }
    }
}

