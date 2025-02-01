package crea8to.princ.magicbundle;

import crea8to.princ.magicbundle.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.text.Text;

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

		ServerWorldEvents.LOAD.register((server, world) -> {
			randomEvent();
		});
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

	public void randomEvent() {
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
                    if (random.nextBoolean()) {
						// action 1
                    } else {
						//action 2
                    }
                    scheduled = false;
					randomEvent();
				}, delay, TimeUnit.MINUTES);
			}
		});
	}

	public void doubleContents() {
	}

	public void createExplosion() {
	}
}