package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class JeevesSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(JeevesSpell.class);
		return desc;
	}

	private final static int DRAW_UP_TO = 3;

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player activePlayer = context.getActivePlayer();
		if (activePlayer.getHand().getCount() >= DRAW_UP_TO) {
			return;
		}
		// card text: At the end of each player's turn, that player draws until
		// they have 3 cards.
		// blue posts confirmed that this is NOT implemented as
		// while(getHand().getCount() < 3)
		// as this would kill fatigued players instantly
		int draw = DRAW_UP_TO - activePlayer.getHand().getCount();
		for (int i = 0; i < draw; i++) {
			context.getLogic().drawCard(activePlayer.getId());
		}
	}

}
