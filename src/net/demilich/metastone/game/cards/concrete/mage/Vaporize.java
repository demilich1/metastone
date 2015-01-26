package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HeroAttackedByMinionTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Vaporize extends SecretCard {

	public Vaporize() {
		super("Vaporize", Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Secret: When a minion attacks your hero, destroy it.");

		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.ATTACKER);
		setTriggerAndEffect(new HeroAttackedByMinionTrigger(), destroySpell);
	}

	@Override
	public int getTypeId() {
		return 74;
	}
}
