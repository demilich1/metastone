package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class DivineSpirit extends SpellCard {

	public DivineSpirit() {
		super("Divine Spirit", Rarity.FREE, HeroClass.PRIEST, 2);
		setSpell(new DivineSpiritSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	private class DivineSpiritSpell implements ISpell {

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			int hpBonus = target.getHp() * 2;
			target.modifyHpBonus(hpBonus);
		}
		
	}

}
