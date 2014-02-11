package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DivineSpirit extends SpellCard {

	private class DivineSpiritSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Actor target) {
			int hpBonus = target.getHp() * 2;
			target.modifyHpBonus(hpBonus);
		}
		
	}
	
	public DivineSpirit() {
		super("Divine Spirit", Rarity.FREE, HeroClass.PRIEST, 2);
		setSpell(new DivineSpiritSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
