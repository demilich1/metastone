package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HuntersMark extends SpellCard {

	private class HuntersMarkSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Actor target) {
			target.setHp(1);
			target.setMaxHp(1);
			target.removeTag(GameTag.HP_BONUS);
		}
		
	}
	
	public HuntersMark() {
		super("Hunter's Mark", Rarity.FREE, HeroClass.HUNTER, 0);
		setSpell(new HuntersMarkSpell());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

}
