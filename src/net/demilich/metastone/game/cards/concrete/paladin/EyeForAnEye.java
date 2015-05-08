package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.trigger.HeroDamagedTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class EyeForAnEye extends SecretCard {

	public EyeForAnEye() {
		super("Eye for an Eye", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When your hero takes damage, deal that much damage to the enemy hero.");

		setSecret(new Secret(new HeroDamagedTrigger(null), DamageSpell.create(EntityReference.EVENT_TARGET, 0), this));
	}

	@Override
	public int getTypeId() {
		return 244;
	}

	// private class EyeForAnEyeSecret extends Secret {
	//
	// public EyeForAnEyeSecret(Card source) {
	// super(new SecretTrigger(new HeroDamagedTrigger()),
	// DamageSpell.create(EntityReference.EVENT_TARGET, 0), source);
	// }
	//
	// @Override
	// protected void onFire(int ownerId, SpellDesc spell, GameEvent event) {
	// DamageEvent damageEvent = (DamageEvent) event;
	// SpellDesc damage = DamageSpell.create(EntityReference.EVENT_TARGET,
	// damageEvent.getDamage());
	// super.onFire(ownerId, spell, event);
	// }
	//
	// }
}
