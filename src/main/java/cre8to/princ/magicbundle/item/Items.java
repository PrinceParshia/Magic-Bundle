package cre8to.princ.magicbundle.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static cre8to.princ.magicbundle.MagicBundle.MOD_ID;

public class Items {
    public static final Item BROKEN_MAGIC_BUNDLE = new Item(new Item.Settings()
            .maxCount(16)
    );
    public static final BundleItem MAGIC_BUNDLE = new MagicBundleItem(new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT)
            .component(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT.apply(compound -> compound.putInt(MagicBundleItem.TIME_TAG, 0)))
    );
    public static final BundleItem USED_MAGIC_BUNDLE = new BundleItem(new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT)
    );
    public static final Item BROKEN_LUCKY_BUNDLE = new Item(new Item.Settings()
            .maxCount(16)
    );
    public static final BundleItem LUCKY_BUNDLE = new LuckyBundleItem(new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT)
            .component(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT.apply(compound -> compound.putInt(MagicBundleItem.TIME_TAG, 0)))
    );
    public static final BundleItem USED_LUCKY_BUNDLE = new BundleItem(new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT)
    );

    public static void register() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "broken_magic_bundle"), BROKEN_MAGIC_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "magic_bundle"), MAGIC_BUNDLE);
        bundleItemModelPredicateProvider(MAGIC_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "used_magic_bundle"), USED_MAGIC_BUNDLE);
        bundleItemModelPredicateProvider(USED_MAGIC_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "broken_lucky_bundle"), BROKEN_LUCKY_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "lucky_bundle"), LUCKY_BUNDLE);
        bundleItemModelPredicateProvider(LUCKY_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "used_lucky_bundle"), USED_LUCKY_BUNDLE);
        bundleItemModelPredicateProvider(USED_LUCKY_BUNDLE);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(BROKEN_MAGIC_BUNDLE);
            entries.add(BROKEN_LUCKY_BUNDLE);
        });
    }

    public static void bundleItemModelPredicateProvider(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.of("filled"),
                (stack, world, entity, seed) -> {
                    BundleContentsComponent bundleContentsComponent = (BundleContentsComponent) stack.get(DataComponentTypes.BUNDLE_CONTENTS);
                    return (bundleContentsComponent == null || bundleContentsComponent.isEmpty()) ? 0.0F : 0.0000001F;
                }
        );
    }
}
