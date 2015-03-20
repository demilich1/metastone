package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Vanish extends SpellCard {

	public Vanish() {
		super("Vanish", Rarity.FREE, HeroClass.ROGUE, 6);
		setDescription("Return all minions to their owner's hand. ");
		setSpell(ReturnMinionToHandSpell.create(EntityReference.ENEMY_MINIONS, 0, false));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 308;
	}
}
