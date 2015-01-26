package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Consecration extends SpellCard {

	public Consecration() {
		super("Consecration", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Deal $2 damage to all enemies.");
		setSpell(DamageSpell.create(2));
		setPredefinedTarget(EntityReference.ENEMY_CHARACTERS);
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 241;
	}
}
