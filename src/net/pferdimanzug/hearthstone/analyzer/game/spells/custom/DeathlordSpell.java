package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;

public class DeathlordSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(DeathlordSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		MinionCard minionCard = (MinionCard) opponent.getDeck().getRandomOfType(CardType.MINION);
		if (minionCard == null) {
			return;
		}
		minionCard.setLocation(CardLocation.VOID);
		opponent.getDeck().remove(minionCard);
		SpellDesc summonSpell = SummonSpell.create(minionCard);
		context.getLogic().castSpell(player.getId(), summonSpell);
	}
	
}