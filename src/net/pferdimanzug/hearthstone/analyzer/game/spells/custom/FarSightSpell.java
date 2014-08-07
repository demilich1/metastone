package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;


public class FarSightSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(FarSightSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card drawnCard = context.getLogic().drawCard(player.getId());
		if (drawnCard == null) {
			return;
		}
		drawnCard.setTag(GameTag.MANA_COST_MODIFIER, -3);
	}

}