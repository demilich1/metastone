package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class CloneMinionSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(CloneMinionSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion template = (Minion) target;
		MinionCard sourceCard = (MinionCard) template.getSourceCard();
		context.getLogic().summon(player.getId(), sourceCard.summon(), null, null, false);
	}

}
