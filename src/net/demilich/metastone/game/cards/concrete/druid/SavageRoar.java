package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SavageRoar extends SpellCard {

	public SavageRoar() {
		super("Savage Roar", Rarity.FREE, HeroClass.DRUID, 3);
		setDescription("Give your characters +2 Attack this turn.");
		SpellDesc buff = TemporaryAttackSpell.create(+2);
		buff.setTarget(EntityReference.FRIENDLY_CHARACTERS);
		setSpell(buff);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 18;
	}
}
