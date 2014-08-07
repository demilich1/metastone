package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MindControl extends SpellCard {

	public MindControl() {
		super("Mind Control", Rarity.FREE, HeroClass.PRIEST, 10);
		setDescription("Take control of an enemy minion.");
		setSpell(MindControlSpell.create());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 271;
	}
}
