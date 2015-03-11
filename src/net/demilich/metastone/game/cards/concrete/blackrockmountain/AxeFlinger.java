package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class AxeFlinger extends MinionCard {

	public AxeFlinger() {
		super("Axe Flinger", 2, 5, Rarity.COMMON, HeroClass.WARRIOR, 4);
		setDescription("Whenever this minion takes damage, deal 2 damage to the enemy hero.");
	}

	@Override
	public Minion summon() {
		Minion axeFlinger = createMinion();
		SpellDesc damageEnemyHero = DamageSpell.create(2);
		damageEnemyHero.setTarget(EntityReference.ENEMY_HERO);
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), damageEnemyHero);
		axeFlinger.setSpellTrigger(trigger);
		return axeFlinger;
	}

}
