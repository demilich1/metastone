package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SetHpSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HuntersMark extends SpellCard {

	public HuntersMark() {
		super("Hunter's Mark", Rarity.FREE, HeroClass.HUNTER, 0);
		setDescription("Change a minion's Health to 1.");
		setSpell(new SetHpSpell(1));
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}



	@Override
	public int getTypeId() {
		return 37;
	}
}
