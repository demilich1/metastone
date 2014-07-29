package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ExplosiveShot extends SpellCard {

	public ExplosiveShot() {
		super("Explosive Shot", Rarity.RARE, HeroClass.HUNTER, 5);
		setDescription("Deal $5 damage to a minion and $2 damage to adjacent ones.");
		setSpell(new ExplosiveShotSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 31;
	}



	private class ExplosiveShotSpell extends Spell {

		private final Spell primary = new DamageSpell(5);
		private final Spell secondary = new DamageSpell(2);

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			primary.setTarget(getTarget());
			context.getLogic().castSpell(player.getId(), primary);
			secondary.cast(context, player, context.getAdjacentMinions(player, target.getReference()));
		}

	}
}
