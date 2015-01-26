package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Frostbolt extends SpellCard {

	public Frostbolt() {
		super("Frostbolt", Rarity.FREE, HeroClass.MAGE, 2);
		setDescription("Deal $3 damage to a character and Freeze it.");
		setSpell(MetaSpell.create(ApplyTagSpell.create(GameTag.FROZEN), DamageSpell.create(3)));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 61;
	}
}
