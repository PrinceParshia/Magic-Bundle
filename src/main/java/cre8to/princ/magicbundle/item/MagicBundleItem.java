package cre8to.princ.magicbundle.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MagicBundleItem extends BundleItem {
    public static final String TIME_TAG = "invTime";
    private static final String MAX_TIME_TAG = "maxInvTime";

    public MagicBundleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (world.isClient) {
            return;
        }

        PlayerEntity player = MinecraftClient.getInstance().player;
        Random random = world.getRandom();
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent) stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (component == null) {
            return;
        }

        if (player != null) {
            stack.set(DataComponentTypes.CUSTOM_DATA, component.apply(compound -> {
                if (!compound.contains(MAX_TIME_TAG) && compound.get(MAX_TIME_TAG) instanceof NbtInt) {
                    compound.putInt(MAX_TIME_TAG, random.nextBetween(200, 600));
                }

                if (compound.contains(TIME_TAG) && compound.get(TIME_TAG) instanceof NbtInt) {
                    int invTime = compound.getInt(TIME_TAG).orElse(0) + 1;
                    int maxTimeTag = compound.getInt(MAX_TIME_TAG).orElse(random.nextBetween(200, 600));

                    if (invTime >= (maxTimeTag - 100)) {
                        if (invTime % 20 == 0) {
                            player.sendMessage(Text.of("<MagicBundle> An Action Happens In... " + (((maxTimeTag - invTime) / 20) + 1)), false);
                        }
                    }

                    if (invTime >= maxTimeTag) {
                        if (random.nextBoolean()) {
                            player.sendMessage(Text.of("<MagicBundle> Bad Luck!"), false);
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
                            if (random.nextBoolean()) {
                                player.sendMessage(Text.of("<MagicBundle> Bad Luck!"), false);
                                world.createExplosion(
                                        null,
                                        entity.getX(),
                                        entity.getY(),
                                        entity.getZ(),
                                        10.0F,
                                        World.ExplosionSourceType.MOB
                                );
                            } else {
                                ItemStack droppedStack = new ItemStack(Items.USED_MAGIC_BUNDLE);
                                droppedStack.set(DataComponentTypes.BUNDLE_CONTENTS, bundleContentsComponent);
                                player.sendMessage(Text.of("<MagicBundle> What a Luck!"), false);
                                entity.dropStack((ServerWorld) world, droppedStack);
                                entity.dropStack((ServerWorld) world, droppedStack.copy());
                            }
                        }

                        player.getInventory().removeOne(stack);
                        compound.remove(TIME_TAG);
                        return;
                    }
                    compound.putInt(TIME_TAG, invTime);
                }
            }));
        }
    }
}