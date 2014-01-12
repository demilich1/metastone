package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Humility extends SpellCard {

	private class HumilitySpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			target.setTag(GameTag.BASE_ATTACK, 1);
			target.setTag(GameTag.ATTACK_BONUS, 0);
		}
		
	}
	
	public Humility() {
		super("Humility", Rarity.FREE, HeroClass.PALADIN, 1);
		setSpell(new HumilitySpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
