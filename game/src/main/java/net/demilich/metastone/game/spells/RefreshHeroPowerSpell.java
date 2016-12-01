package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class RefreshHeroPowerSpell extends Spell {
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int heroPowerUsages = player.getHero().getHeroPower().hasBeenUsed();
		player.getHero().setAttribute(Attribute.HERO_POWER_USAGES, heroPowerUsages + 1);
	}
}
