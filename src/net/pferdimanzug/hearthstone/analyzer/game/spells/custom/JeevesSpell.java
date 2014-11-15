package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
