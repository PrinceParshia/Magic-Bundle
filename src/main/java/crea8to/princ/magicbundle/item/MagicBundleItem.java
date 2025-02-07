package crea8to.princ.magicbundle.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MagicBundleItem extends BundleItem {
    public static final String TIME_TAG = "invTime";

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
        BundleContentsComponent nbt = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (component == null) {
            return;
        }

        stack.set(DataComponentTypes.CUSTOM_DATA, component.apply(compound -> {
            if (compound.contains(TIME_TAG, NbtElement.INT_TYPE)) {
                int invTime = compound.getInt(TIME_TAG) + 1;

                if (invTime > random.nextBetween(100, 120)) {
                    if (random.nextBoolean()) {
                        ItemStack magicBundleNbt = new ItemStack(Items.USED_MAGIC_BUNDLE);
                        magicBundleNbt.set(DataComponentTypes.BUNDLE_CONTENTS, nbt);
                        entity.dropStack(magicBundleNbt);
                        entity.dropStack(magicBundleNbt);
                    }
                    else {
                        world.createExplosion(
                                null,
                                MinecraftClient.getInstance().player.getX(),
                                MinecraftClient.getInstance().player.getY(),
                                MinecraftClient.getInstance().player.getZ(),
                                7.0F,
                                World.ExplosionSourceType.MOB
                        );
                    }

                    ((PlayerEntity) entity).getInventory().removeOne(stack);
                    compound.remove(TIME_TAG);
                    return;
                }
                compound.putInt(TIME_TAG, invTime);
            }
        }));
    }
}