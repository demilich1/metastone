package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter.Hound;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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