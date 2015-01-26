package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.mage.Sheep;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Polymorph extends SpellCard {

	public Polymorph() {
		super("Polymorph", Rarity.FREE, HeroClass.MAGE, 4);
		setDescription("Transform a minion into a 1/1 Sheep.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(TransformMinionSpell.create(new Sheep()));
	}
	
	@Override
	public int getTypeId() {
		return 70;
	}
}
