package crea8to.princ.magicbundle;

import crea8to.princ.magicbundle.item.Items;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootTable {
    private static final Identifier WOODLAND_MANSION_LOOT_TABLE_ID = Identifier.of("minecraft", "chests/woodland_mansion");

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder) -> {
            if (WOODLAND_MANSION_LOOT_TABLE_ID.equals(id)) {
                LootPool customLootPool = LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1, 3))
                        .with(ItemEntry.builder(Items.BROKEN_MAGIC_BUNDLE)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3)))
                        ).build();

                lootManager.pool(customLootPool);
            }
        });
    }
}
