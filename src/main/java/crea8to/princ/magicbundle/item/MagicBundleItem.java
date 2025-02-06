package crea8to.princ.magicbundle.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class MagicBundleItem extends BundleItem {
    public MagicBundleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound getMagicBundleData = stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt();
        World invtimevariable = getMagicBundleData.contains("i dont understand..");

        if (invtimevariable > world.getRandom().nextIntBetween(2400, 6000)) {}
    }
}
