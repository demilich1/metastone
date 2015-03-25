package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class EmergencyCoolant extends SpellCard {

	public EmergencyCoolant() {
		super("Emergency Coolant", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Freeze a minion.");

		setSpell(AddAttributeSpell.create(GameTag.FROZEN));
		setTargetRequirement(TargetSelection.MINIONS);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 584;
	}
}
