package cre8to.princ.magicbundle;

import cre8to.princ.magicbundle.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class MagicBundle implements ModInitializer {
	public static final String MOD_ID = "magic-bundle";

	@Override
	public void onInitialize() {
		Items.register();
		modifyLootTable();
	}

	private void modifyLootTable() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(LootTables.WOODLAND_MANSION_CHEST)) {
				LootPool poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(Items.BROKEN_MAGIC_BUNDLE))
						.rolls(ConstantLootNumberProvider.create(1))
						.build();

				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin()) {
				if (key.equals(LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY)) {
					tableBuilder.modifyPools(poolBuilder -> {
						poolBuilder.with(ItemEntry.builder(Items.BROKEN_LUCKY_BUNDLE));
					});
				}
			}
		});
	}
}