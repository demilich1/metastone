package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class Counterspell extends SecretCard {

	public Counterspell() {
		super("Counterspell", Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Secret: When your opponent casts a spell, Counter it.");

		SpellDesc counterSpell = AddAttributeSpell.create(EntityReference.PENDING_CARD, GameTag.COUNTERED);
		// set targetPlayer to OPPONENT
		setSecret(new Secret(new SpellCastedTrigger(null), counterSpell, this));
	}

	@Override
	public int getTypeId() {
		return 57;
	}
}
