package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class GurubashiBerserker extends MinionCard {
	
	public static final int BASE_ATTACK = 2;
	public static final int ATTACK_BONUS = 3;

	public GurubashiBerserker() {
		super("Gurubashi Berserker", BASE_ATTACK, 7, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Whenever this minion takes damage, gain +3 Attack.");
	}
	
	@Override
	public int getTypeId() {
		return 138;
	}
	
	@Override
	public Minion summon() {
		Minion gurubashiBerserker = createMinion();
		SpellDesc buffAttack = BuffSpell.create(EntityReference.SELF, ATTACK_BONUS, 0);
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(null), buffAttack);
		gurubashiBerserker.setSpellTrigger(trigger);
		return gurubashiBerserker;
	}
}
