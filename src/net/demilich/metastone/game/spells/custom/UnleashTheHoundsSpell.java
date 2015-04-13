package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class UnleashTheHoundsSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(UnleashTheHoundsSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int enemyMinions = context.getMinionCount(context.getOpponent(player));
		for (int i = 0; i < enemyMinions; i++) {
			MinionCard hound = (MinionCard) CardCatalogue.getCardByName("minion_hound");
			context.getLogic().summon(player.getId(), hound.summon());
		}
	}
}