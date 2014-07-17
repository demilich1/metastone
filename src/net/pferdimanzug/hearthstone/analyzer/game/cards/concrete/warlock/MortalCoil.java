package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MortalCoil extends SpellCard {

	private class MortalCoilSpell extends DamageSpell {

		public MortalCoilSpell() {
			super(1);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			super.onCast(context, player, target);
			Actor targetActor = (Actor) target;
			if (targetActor.isDead()) {
				context.getLogic().drawCard(player.getId());
			}
		}
		
	}
	
	public MortalCoil() {
		super("Mortal Coil", Rarity.FREE, HeroClass.WARLOCK, 1);
		setDescription("Deal $1 damage to a minion. If that kills it, draw a card.");
		setSpell(new MortalCoilSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 345;
	}
}
