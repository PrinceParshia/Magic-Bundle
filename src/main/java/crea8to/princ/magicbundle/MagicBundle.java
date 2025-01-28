package crea8to.princ.magicbundle;

import crea8to.princ.magicbundle.item.Items;
import net.fabricmc.api.ModInitializer;

public class MagicBundle implements ModInitializer {
	public static final String MOD_ID = "magic-bundle";

	@Override
	public void onInitialize() {
		Items.register();
		LootTable.register();
	}
}