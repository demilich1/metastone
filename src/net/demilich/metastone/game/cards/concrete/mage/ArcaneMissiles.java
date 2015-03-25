package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MissilesSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArcaneMissiles extends SpellCard {

	public ArcaneMissiles() {
		super("Arcane Missiles", Rarity.FREE, HeroClass.MAGE, 1);
		setDescription("Deal $3 damage randomly split among enemy characters.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(MissilesSpell.create(EntityReference.ENEMY_CHARACTERS, 1, 3));
	}

	@Override
	public int getTypeId() {
		return 53;
	}
}
