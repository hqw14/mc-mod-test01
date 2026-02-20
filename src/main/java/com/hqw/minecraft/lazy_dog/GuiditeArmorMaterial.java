package com.hqw.minecraft.lazy_dog;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class GuiditeArmorMaterial {
    public static final int BASE_DURABILITY = 99;
    public static final ResourceKey<EquipmentAsset> GUIDITE_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(LazyDog.MOD_ID, "guidite"));
    public static final TagKey<Item> REPAIRS_GUIDITE_ARMOR = TagKey.create(BuiltInRegistries.ITEM.key(),ResourceLocation.fromNamespaceAndPath(LazyDog.MOD_ID, "repairs_guidite_armor"));
    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
            BASE_DURABILITY,
            Map.of(
                    ArmorType.HELMET, 20,
                    ArmorType.CHESTPLATE, 80,
                    ArmorType.LEGGINGS, 60,
                    ArmorType.BOOTS, 20
            ),
            999999999,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            99.0F,
            REPAIRS_GUIDITE_ARMOR,
            GUIDITE_ARMOR_MATERIAL_KEY
    );
}
