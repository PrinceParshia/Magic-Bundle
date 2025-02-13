package crea8to.princ.magicbundle.item;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.util.Identifier;

public class UsedMagicBundleItem extends BundleItem {
    public UsedMagicBundleItem(Settings settings) {
        super(settings);
    }

    public static void registerModelPredicate() {
        ModelPredicateProviderRegistry.register(Items.USED_MAGIC_BUNDLE, Identifier.of("filled"),
                (stack, world, entity, seed) -> {
                    BundleContentsComponent bundleComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
                    return (bundleComponent == null || bundleComponent.isEmpty()) ? 0.0F : 0.0000001F;
                }
        );
    }
}
