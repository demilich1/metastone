package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageRandomSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AvengingWrath extends SpellCard {

	public AvengingWrath() {
		super("Avenging Wrath", Rarity.EPIC, HeroClass.PALADIN, 6);
		setDescription("Deal 8 damage randomly split among enemy characters.");
		
		setTargetRequirement(TargetSelection.NONE);
		setSpell(DamageRandomSpell.create(EntityReference.ENEMY_CHARACTERS, 1, 8));
	}

	@Override
	public int getTypeId() {
		return 236;
	}
}
