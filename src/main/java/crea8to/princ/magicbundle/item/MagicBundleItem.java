package crea8to.princ.magicbundle.item;

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

public class MagicBundleItem extends BundleItem {
    public static final String TIME_TAG = "invTime";
    private static final String MAX_TIME_TAG = "maxInvTime";

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
        BundleContentsComponent bundleComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (component == null) {
            return;
        }

        stack.set(DataComponentTypes.CUSTOM_DATA, component.apply(compound -> {
            if (!compound.contains(MAX_TIME_TAG, NbtElement.INT_TYPE)) {
                compound.putInt(MAX_TIME_TAG, random.nextBetween(2500, 6100));
            }

            if (compound.contains(TIME_TAG, NbtElement.INT_TYPE)) {
                int invTime = compound.getInt(TIME_TAG) + 1;
                int maxTimeTag = compound.getInt(MAX_TIME_TAG);

                if (invTime >= (maxTimeTag - 100)) {
                    if (invTime % 20 == 0) {
                        entity.sendMessage(Text.of("<MagicBundle> An Action Happens In... " + (((maxTimeTag - invTime) / 20) + 1)));
                    }
                }

                if (invTime >= maxTimeTag) {
                    if (random.nextBoolean()) {
                        entity  .sendMessage(Text.of("<MagicBundle> Bad Luck!"));
                        world.createExplosion(
                                null,
                                entity.getX(),
                                entity.getY(),
                                entity.getZ(),
                                7.0F,
                                World.ExplosionSourceType.MOB
                        );
                    }
                    else {
                        ItemStack droppedStack = new ItemStack(Items.USED_MAGIC_BUNDLE);
                        droppedStack.set(DataComponentTypes.BUNDLE_CONTENTS, bundleComponent);
                        entity.sendMessage(Text.of("<MagicBundle> What a Luck!"));
                        entity.dropStack(droppedStack);
                        entity.dropStack(droppedStack.copy());
                    }

                    if (entity instanceof PlayerEntity player) {
                        player.getInventory().removeOne(stack);
                    }
                    compound.remove(TIME_TAG);
                    return;
                }
                compound.putInt(TIME_TAG, invTime);
            }
        }));
    }
}