package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SetHpSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Equality extends SpellCard {

	public Equality() {
		super("Equality", Rarity.RARE, HeroClass.PALADIN, 2);
		setDescription("Change the Health of ALL minions to 1.");
		SpellDesc spell = SetHpSpell.create(1);
		spell.setTarget(EntityReference.ALL_MINIONS);
		setSpell(spell);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 243;
	}
}
