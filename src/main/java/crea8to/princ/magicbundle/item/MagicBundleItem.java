package crea8to.princ.magicbundle.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Random;

public class MagicBundleItem extends BundleItem {
    public MagicBundleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound getMagicBundleData = stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt();

        int invTime = 0;
        if (getMagicBundleData.contains("invTime")) {
            invTime = getMagicBundleData.getInt("invTime");
        } else {
            stack.get(DataComponentTypes.CUSTOM_DATA).apply(nbt -> nbt.putInt("invTime", 0));
        }

        if (invTime > world.getRandom().nextBetween(2400, 6000)) {
            if (new Random().nextBoolean()) {

            } else {
                
            }
        }
    }
}
