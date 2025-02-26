package crea8to.princ.magicbundle.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LuckyBundleItem extends BundleItem {
    public static final String TIME_TAG = "invTime";
    private static final String MAX_TIME_TAG = "maxInvTime";

    public LuckyBundleItem(Settings settings) {
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
            if (!compound.contains(MAX_TIME_TAG, NbtElement.INT_TYPE)) {
                compound.putInt(MAX_TIME_TAG, random.nextBetween(100, 200));
            }

            if (compound.contains(TIME_TAG, NbtElement.INT_TYPE)) {
                int invTime = compound.getInt(TIME_TAG) + 1;
                int maxTimeTag = compound.getInt(MAX_TIME_TAG);

                if (invTime >= (maxTimeTag - 100)) {
                    if (invTime % 20 == 0) {
                        entity.sendMessage(Text.of("<LuckyBundle> An Action Happens In... " + (((maxTimeTag - invTime) / 20) + 1)));
                    }
                }

                if (invTime >= maxTimeTag) {
                    if (random.nextBoolean()) {
                        ItemStack diamond = new ItemStack(net.minecraft.item.Items.DIAMOND, 16);
                        ItemStack netheriteIngot = new ItemStack(net.minecraft.item.Items.NETHERITE_INGOT, 4);
                        ItemStack netherStar = new ItemStack(net.minecraft.item.Items.NETHER_STAR);
                        ItemStack beacon = new ItemStack(net.minecraft.item.Items.BEACON);
                        ItemStack loot = new ItemStack(net.minecraft.item.Items.STICK);
                        ItemStack loot2 = new ItemStack(net.minecraft.item.Items.LEAD);
                        ItemStack loot3 = new ItemStack(net.minecraft.item.Items.STRING);
                        if (random.nextBoolean()) {
                            loot = diamond;
                        } else {
                            if (random.nextBoolean()) {
                                loot = diamond;
                                loot2 = netherStar;
                            } else {
                                if (random.nextBoolean()) {
                                    loot = netheriteIngot;
                                    loot2 = beacon;
                                } else {
                                    loot = diamond;
                                    loot2 = netheriteIngot;
                                    loot3 = netherStar;
                                }
                            }
                        }

                        ItemStack droppedStack = new ItemStack(Items.USED_LUCKY_BUNDLE);
                        BundleContentsComponent bundleContentsComponent = droppedStack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(new ArrayList<>()));
                        List<ItemStack> updatedItems = new ArrayList<>();
                        bundleContentsComponent.iterate().forEach(updatedItems::add);
                        updatedItems.add(loot);
                        updatedItems.add(loot2);
                        updatedItems.add(loot3);
                        droppedStack.set(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(updatedItems));
                        entity.sendMessage(Text.of("<LuckyBundle> What a Luck!"));
                        entity.dropStack(droppedStack);
                    }
                    else {
                        if (random.nextBoolean()) {
                            ItemStack diamondBlock = new ItemStack(net.minecraft.item.Items.DIAMOND, 8);
                            ItemStack netheriteIngot = new ItemStack(net.minecraft.item.Items.NETHERITE_INGOT, 8);
                            ItemStack netherLeggings = new ItemStack(net.minecraft.item.Items.NETHERITE_LEGGINGS);
                            ItemStack emeraldBlock = new ItemStack(net.minecraft.item.Items.EMERALD_BLOCK, 64);
                            ItemStack loot = new ItemStack(net.minecraft.item.Items.STICK);
                            ItemStack loot2 = new ItemStack(net.minecraft.item.Items.LEAD);
                            ItemStack loot3 = new ItemStack(net.minecraft.item.Items.STRING);
                            if (random.nextBoolean()) {
                                loot = diamondBlock;
                            } else {
                                if (random.nextBoolean()) {
                                    loot = diamondBlock;
                                    loot2 = netherLeggings;
                                } else {
                                    if (random.nextBoolean()) {
                                        loot = netheriteIngot;
                                        loot2 = emeraldBlock;
                                    } else {
                                        loot = diamondBlock;
                                        loot2 = netheriteIngot;
                                        loot3 = netherLeggings;
                                    }
                                }
                            }

                            ItemStack droppedStack = new ItemStack(Items.USED_LUCKY_BUNDLE);
                            BundleContentsComponent bundleContentsComponent = droppedStack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(new ArrayList<>()));
                            List<ItemStack> updatedItems = new ArrayList<>();
                            bundleContentsComponent.iterate().forEach(updatedItems::add);
                            updatedItems.add(loot);
                            updatedItems.add(loot2);
                            updatedItems.add(loot3);
                            droppedStack.set(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(updatedItems));
                            entity.sendMessage(Text.of("<MagicBundle> What a Luck!"));
                            entity.dropStack(droppedStack);
                        } else {
                            CreeperEntity creeper = EntityType.CREEPER.create(world);
                            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                            if (creeper != null) {
                                creeper.refreshPositionAndAngles(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ(), 0, 0);
                                creeper.onStruckByLightning((ServerWorld) world, lightningEntity);
                                world.spawnEntity(creeper);
                            }
                        }
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