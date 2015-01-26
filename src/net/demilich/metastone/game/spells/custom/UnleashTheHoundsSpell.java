package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.concrete.tokens.hunter.Hound;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class UnleashTheHoundsSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(UnleashTheHoundsSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int enemyMinions = context.getMinionCount(context.getOpponent(player));
		for (int i = 0; i < enemyMinions; i++) {
			context.getLogic().summon(player.getId(), new Hound().summon());
		}
	}
}