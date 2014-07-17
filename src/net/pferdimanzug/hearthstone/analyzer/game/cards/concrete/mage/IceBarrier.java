package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HeroAttackedTrigger;

public class IceBarrier extends SecretCard {

	public IceBarrier() {
		super("Ice Barrier", Rarity.COMMON, HeroClass.MAGE, 3);
		setDescription("Secret: As soon as your hero is attacked, gain 8 Armor.");
		setTriggerAndEffect(new HeroAttackedTrigger(), new BuffHeroSpell(0, 8));
	}



	@Override
	public int getTypeId() {
		return 63;
	}
}
