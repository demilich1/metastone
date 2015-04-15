package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public abstract class ValueProvider {
	
	protected final ValueProviderDesc desc;

	public ValueProvider(ValueProviderDesc desc) {
		this.desc = desc;
	}
	
	//TODO: remove once all cards have been externalized
	public ValueProvider() {
		this.desc = null;
	}
	
	public abstract int provideValue(GameContext context, Player player, Entity target);
	
}
