package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BlessedChampion extends SpellCard {

	public BlessedChampion() {
		super("Blessed Champion", Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Double a minion's Attack.");
		
		setSpell(new DoubleAttackSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	private class DoubleAttackSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor targetActor = (Actor) target;
			target.modifyTag(GameTag.ATTACK_BONUS, targetActor.getAttack());
		}
		
	}

}
