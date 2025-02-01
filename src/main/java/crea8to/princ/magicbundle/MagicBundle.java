package crea8to.princ.magicbundle;

import crea8to.princ.magicbundle.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MagicBundle implements ModInitializer {
	public static final String MOD_ID = "magic-bundle";
	private final Random random = new Random();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private boolean scheduled = false;
	private int delay = 2;

	@Override
	public void onInitialize() {
		Items.register();
		LootTable();
		randomEvent();
	}

	public void LootTable() {
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

	public  void randomEvent() {
		ServerTickEvents.END_SERVER_TICK.register((server) -> {
			if (!scheduled) {
				scheduled = true;
				switch (random.nextInt(4)) {
					case 0:
						delay = 2;
						break;
					case 1:
						delay = 3;
						break;
					case 2:
						delay = 4;
						break;
					case 3:
						delay = 5;
						break;
					default:
						delay = 2;
						break;
				}

				scheduler.schedule(() -> {
					random.nextBoolean() ?
							action1 :
							action2
									(server);
					scheduled = false;
				}, delay, TimeUnit.MINUTES);
			}
		});
	}
}