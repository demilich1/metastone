package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HeroDamagedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.SecretTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EyeForAnEye extends SecretCard {

	private class EyeForAnEyeSecret extends Secret {

		public EyeForAnEyeSecret(Card source) {
			super(new SecretTrigger(new HeroDamagedTrigger()), new DamageSpell(0), source);
		}

		@Override
		protected EntityReference getTargetForSpell(GameEvent event) {
			return EntityReference.ENEMY_HERO;
		}

		@Override
		protected void onFire(int ownerId, Spell spell, GameEvent event) {
			DamageSpell damageSpell = (DamageSpell) spell;
			DamageEvent damageEvent = (DamageEvent) event;
			damageSpell.setDamage(damageEvent.getDamage());
			super.onFire(ownerId, spell, event);
		}

	}

	public EyeForAnEye() {
		super("Eye for an Eye", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When your hero takes damage, deal that much damage to the enemy hero.");

		setSecret(new EyeForAnEyeSecret(this));
	}

}
