package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.AnyMinionAttacksTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class FreezingTrap extends SecretCard {

	public FreezingTrap() {
		super("Freezing Trap", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Secret: When an enemy minion attacks, return it to its owner's hand and it costs (2) more.");
		
		SpellDesc returnToHandSpell = ReturnMinionToHandSpell.create(EntityReference.ATTACKER, 2, false);
		setSecret(new Secret(new AnyMinionAttacksTrigger(null), returnToHandSpell, this));
	}

	@Override
	public int getTypeId() {
		return 34;
	}
}
