package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MindControlSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MindControl extends SpellCard {

	public MindControl() {
		super("Mind Control", Rarity.FREE, HeroClass.PRIEST, 10);
		setDescription("Take control of an enemy minion.");
		setSpell(MindControlSpell.create(null, false));
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 271;
	}
}
