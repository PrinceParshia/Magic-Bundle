package crea8to.princ.magicbundle.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MagicBundleItem extends BundleItem {

    public static final String TIME_TAG = "invTime";

    public MagicBundleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }

        Random random = world.getRandom();
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component == null) {
            return;
        }

        stack.set(DataComponentTypes.CUSTOM_DATA, component.apply(compound -> {
            if (compound.contains(TIME_TAG, NbtElement.INT_TYPE)) {
                int invTime = compound.getInt(TIME_TAG) + 1;

                if (invTime > random.nextBetween(2400, 6000)) {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("hell"), false);

                    if (random.nextBoolean()) {
                        // action 1
                    }
                    else {
                        // action 2
                    }

                    compound.remove(TIME_TAG); // When you add item deleting from the inv it should replace this line
                    return;
                }
                compound.putInt(TIME_TAG, invTime);
            }
        }));
    }
}
