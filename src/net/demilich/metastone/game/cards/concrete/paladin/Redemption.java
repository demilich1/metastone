package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ReviveMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class Redemption extends SecretCard {

	public Redemption() {
		super("Redemption", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When one of your minions dies, return it to life with 1 Health.");
		
		SpellDesc reviveSpell = ReviveMinionSpell.create(EntityReference.KILLED_MINION, 1);
		setSecret(new Secret(new MinionDeathTrigger(null), reviveSpell, this));
	}

	@Override
	public int getTypeId() {
		return 254;
	}
}
