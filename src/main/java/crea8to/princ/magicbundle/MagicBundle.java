package crea8to.princ.magicbundle;

import crea8to.princ.magicbundle.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MagicBundle implements ModInitializer {
	public static final String MOD_ID = "magic-bundle";
	private final Random random = new Random();
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final MinecraftClient client = MinecraftClient.getInstance();
	private boolean scheduled = false;
	private boolean hasMagicBundle = false;
	private final List<ScheduledFuture<?>> scheduledTasks = new ArrayList<>();

	@Override
	public void onInitialize() {
		Items.register();
		modifyLootTable();

//		ServerWorldEvents.LOAD.register((server, world) -> {
//			randomEventManager();
//		});
	}

	private void modifyLootTable() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(LootTables.WOODLAND_MANSION_CHEST)) {
				LootPool brokenMagicBundle = LootPool.builder()
						.with(ItemEntry.builder(Items.BROKEN_MAGIC_BUNDLE)
								.weight(1))
						.rolls(ConstantLootNumberProvider.create(1))
						.build();

				tableBuilder.pool(brokenMagicBundle);
			}
		});
	}

//	private void randomEventManager() {
//		ServerTickEvents.END_SERVER_TICK.register((server) -> {
//			if (client.player != null) {
//				int magicBundleCount = getMagicBundleCount();
//
//				if (magicBundleCount == 1 && !hasMagicBundle) {
//					hasMagicBundle = true;
//					startScheduledEvent();
//				} else if (magicBundleCount > 1 && hasMagicBundle) {
//					hasMagicBundle = false;
//					cancelScheduledTasks();
//					client.player.sendMessage(Text.of("<MagicBundle> You must have exactly one Magic Bundle to continue having its facilities."), false);
//				} else if (magicBundleCount == 0 && hasMagicBundle) {
//					hasMagicBundle = false;
//					cancelScheduledTasks();
//				}
//			}
//		});
//	}
//
//	private void startScheduledEvent() {
//		if (!scheduled) {
//			scheduled = true;
//			client.player.sendMessage(Text.of("<MagicBundle> Be Ready!"));
//			scheduleTasks();
//		}
//	}
//
//	private void scheduleTasks() {
//		int delay = 0;
//		int countdownStartFrom = 5;
//
//		switch (random.nextInt(4)) {
//			case 0:
//				delay = 2;
//				break;
//			case 1:
//				delay = 3;
//				break;
//			case 2:
//				delay = 4;
//				break;
//			case 3:
//				delay = 5;
//				break;
//			default:
//				delay = 2;
//				break;
//		}
//
//		if (!scheduler.isShutdown()) {
//			ScheduledFuture<?> initialTask = scheduler.schedule(() -> {
//				for (int i = countdownStartFrom; i > 0; i--) {
//					final int count = i;
//					if (!scheduler.isShutdown()) {
//						ScheduledFuture<?> countdownTask = scheduler.schedule(() -> {
//							client.player.sendMessage(Text.of("<MagicBundle> Something Will Happen In " + count + "..."), false);
//						}, countdownStartFrom - count, TimeUnit.SECONDS);
//						scheduledTasks.add(countdownTask);
//					}
//				}
//
//				if (!scheduler.isShutdown()) {
//					ScheduledFuture<?> finalTask = scheduler.schedule(() -> {
//						if (hasMagicBundle) {
//							if (random.nextBoolean()) {
//								//action 1
//							} else {
//								createExplosion();
//								removeMagicBundleFromInventory();
//							}
//						}
//						scheduled = false; // Reset the scheduled flag
//						if (getMagicBundleCount() == 1) {
//							startScheduledEvent();
//						}
//					}, countdownStartFrom, TimeUnit.SECONDS);
//					scheduledTasks.add(finalTask);
//				}
//			}, delay, TimeUnit.SECONDS);
//			scheduledTasks.add(initialTask);
//		}
//	}
//
//	private void cancelScheduledTasks() {
//		for (ScheduledFuture<?> task : scheduledTasks) {
//			task.cancel(true);
//		}
//		scheduledTasks.clear();
//		scheduler.shutdownNow();
//		try {
//			if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
//				scheduler.shutdownNow();
//			}
//		} catch (InterruptedException e) {
//			scheduler.shutdownNow();
//			Thread.currentThread().interrupt();
//		}
//		scheduler = Executors.newScheduledThreadPool(1);
//		scheduled = false;
//	}
//
//	private void createExplosion() {
//		if (client.player != null) {
//			client.player.sendMessage(Text.of("<MagicBundle> Bad Luck...!"));
//			client.world.createExplosion(null, client.player.getBlockPos().getX(), client.player.getBlockPos().getY(), client.player.getBlockPos().getZ(), 9.0F, true, ExplosionSourceType.NONE);
//		}
//	}
//
//	private int getMagicBundleCount() {
//		int count = 0;
//		for (int i = 0; i < client.player.getInventory().size(); i++) {
//			ItemStack stack = client.player.getInventory().getStack(i);
//			if (stack.getItem() == Items.MAGIC_BUNDLE) {
//				count += stack.getCount();
//			}
//		}
//		return count;
//	}
//
//	private void removeMagicBundleFromInventory() {
//		if (client.player != null) {
//			for (int i = 0; i < client.player.getInventory().size(); i++) {
//				ItemStack stack = client.player.getInventory().getStack(i);
//				if (stack.getItem() == Items.MAGIC_BUNDLE) {
//					client.player.getInventory().removeStack(i);
//				}
//			}
//		}
//	}
}