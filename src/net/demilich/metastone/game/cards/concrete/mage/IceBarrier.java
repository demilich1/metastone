package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.trigger.HeroAttackedTrigger;

public class IceBarrier extends SecretCard {

	public IceBarrier() {
		super("Ice Barrier", Rarity.COMMON, HeroClass.MAGE, 3);
		setDescription("Secret: As soon as your hero is attacked, gain 8 Armor.");
		setTriggerAndEffect(new HeroAttackedTrigger(), BuffHeroSpell.create(0, 8));
	}

	@Override
	public int getTypeId() {
		return 63;
	}
}
