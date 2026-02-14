package com.hqw.minecraft.test01;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import javax.swing.*;
import java.util.function.Function;

import static net.fabricmc.loader.impl.FabricLoaderImpl.MOD_ID;

public class Sword {
    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Hqwtest01.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
    private static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                // Standard Sword Damage & Speed
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, "attack_damage"), 10000000000000.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, "attack_speed"), -10000000, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)

                // Custom REACH (Hit Range)
                // Default survival reach is 3.0. This adds 1.5 for a total of 4.5 blocks.
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, "reach_distance"), 100000000.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }
    public static final Item jjjsword = register(
            "jjjsword",
            Item::new,
            new Item.Properties().attributes(createAttributes())
            //new Item.Properties().sword(ToolMaterial.DIAMOND, 1000f, 0.01f)
    );
    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((itemGroup) -> itemGroup.accept(Sword.jjjsword));

    }

}
