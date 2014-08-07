package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class VoidcallerSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(VoidcallerSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardCollection demonsInHand = SpellUtils.getCards(player.getHand(), card -> card.getTag(GameTag.RACE) == Race.DEMON);
		if (demonsInHand.isEmpty()) {
			return;
		}
		
		MinionCard randomDemonCard = (MinionCard) demonsInHand.getRandom();
		context.getLogic().summon(player.getId(), randomDemonCard.summon(), null, null, false);
	}
	
}
