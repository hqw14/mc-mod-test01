package com.hqw.minecraft.lazy_dog;


import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class HQWArmor {
    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(LazyDog.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
    public static final Item GUIDITE_HELMET = register("guidite_helmet",
            Item::new,
            new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    );
    public static final Item GUIDITE_CHESTPLATE = register("guidite_chestplate",
            Item::new,
            new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    );

    public static final Item GUIDITE_LEGGINGS = register("guidite_leggings",
            Item::new,
            new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    );

    public static final Item GUIDITE_BOOTS = register("guidite_boots",
            Item::new,
            new Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    );

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(HQWArmor.GUIDITE_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(HQWArmor.GUIDITE_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(HQWArmor.GUIDITE_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(HQWArmor.GUIDITE_HELMET));

        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, amount, afterHealth, damageSource) -> {
            // 1. 检查世界是否为服务端且实体是否存在
            if (!entity.level().isClientSide()) {
                Entity attacker = source.getEntity();
                // 3. 核心判定：是你的护甲，且伤害来源不是爆炸（防止死循环爆炸）
                if (entity.getItemBySlot(EquipmentSlot.CHEST).is(HQWArmor.GUIDITE_CHESTPLATE)&&attacker instanceof LivingEntity&& !source.is(DamageTypes.EXPLOSION)&& !source.is(DamageTypes.FALL)) {
                    // 执行爆炸
                    entity.level().explode(
                            entity,
                            null,
                            null,
                            entity.getX(), entity.getY(), entity.getZ(),
                            100.0F, // 爆炸威力
                            false,
                            Level.ExplosionInteraction.MOB
                    );
                }
            }
        });

    }


}
