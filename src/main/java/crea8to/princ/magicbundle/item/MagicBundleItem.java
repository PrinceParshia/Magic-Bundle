package crea8to.princ.magicbundle.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class MagicBundleItem extends BundleItem {
    public MagicBundleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound getMagicBundleData = stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt();

        if (getMagicBundleData.contains("invTime")) {
            int invTime = getMagicBundleData.getInt("invTime");
            getMagicBundleData.putInt("invTime", invTime++);


            if (invTime > world.getRandom().nextBetween(100, 120)) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("hell"), false);
            }
        } else {
            stack.get(DataComponentTypes.CUSTOM_DATA).apply(nbt -> nbt.putInt("invTime", 0));
        }

    }
}
