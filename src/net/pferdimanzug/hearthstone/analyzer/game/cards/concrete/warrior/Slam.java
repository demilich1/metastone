package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Slam extends SpellCard {

	public Slam() {
		super("Slam", Rarity.COMMON, HeroClass.WARRIOR, 2);
		setDescription("Deal $2 damage to a minion. If it survives, draw a card.");
		setSpell(new SlamSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	private class SlamSpell extends DamageSpell {

		public SlamSpell() {
			super(2);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			super.onCast(context, player, target);
			Actor targetActor = (Actor) target;
			if (!targetActor.isDead()) {
				context.getLogic().drawCard(player.getId());
			}
		}

	}

}
