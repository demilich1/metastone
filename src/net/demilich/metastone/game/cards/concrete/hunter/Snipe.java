package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionCardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class Snipe extends SecretCard {

	public Snipe() {
		super("Snipe", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Secret: When your opponent plays a minion, deal $4 damage to it.");
		
		SpellDesc damageSpell = DamageSpell.create(EntityReference.EVENT_TARGET, 4);
		setSecret(new Secret(new MinionCardPlayedTrigger(null), damageSpell, this));
	}

	@Override
	public int getTypeId() {
		return 45;
	}
}
