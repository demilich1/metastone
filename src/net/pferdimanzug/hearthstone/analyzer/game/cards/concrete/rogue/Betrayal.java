package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Betrayal extends SpellCard {
	
	private class BetrayalSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			for (Entity adjacentMinion : context.getAdjacentMinions(player, target.getReference())) {
				context.getLogic().fight(player, (Actor) target, (Actor) adjacentMinion);
			}
		}
		
	}

	public Betrayal() {
		super("Betrayal", Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("An enemy minion deals its damage to the minions next to it.");
		setSpell(new BetrayalSpell());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

}


