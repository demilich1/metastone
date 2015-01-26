package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveTagSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HealingTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Shadowboxer extends MinionCard {

	public Shadowboxer() {
		super("Shadowboxer", 2, 3, Rarity.COMMON, HeroClass.PRIEST, 2);
		setDescription("Whenever a character is healed, deal 1 damage to a random enemy.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 562;
	}

	@Override
	public Minion summon() {
		Minion shadowboxer = createMinion();
		SpellDesc damageRandomEnemy = DamageSpell.create(1);
		damageRandomEnemy.setTarget(EntityReference.ENEMY_CHARACTERS);
		damageRandomEnemy.pickRandomTarget(true);
		SpellDesc removeStealth = RemoveTagSpell.create(GameTag.STEALTHED);
		removeStealth.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new HealingTrigger(), MetaSpell.create(damageRandomEnemy, removeStealth));
		shadowboxer.setSpellTrigger(trigger);
		return shadowboxer;
	}
}
