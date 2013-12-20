package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class HuntersMark extends SpellCard {

	public HuntersMark() {
		super("Hunter's Mark", Rarity.FREE, HeroClass.HUNTER, 0);
		setSpell(new HuntersMarkSpell());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}
	
	private class HuntersMarkSpell implements ISpell {

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			target.setHp(1);
			target.setMaxHp(1);
			target.removeTag(GameTag.HP_BONUS);
		}
		
	}

}
