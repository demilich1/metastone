package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageCausedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Snowchugger extends MinionCard {

	public Snowchugger() {
		super("Snowchugger", 2, 3, Rarity.COMMON, HeroClass.MAGE, 2);
		setDescription("Freeze any character damaged by this minion.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 496;
	}

	@Override
	public Minion summon() {
		Minion snowchugger = createMinion();
		SpellDesc freezeSpell = AddAttributeSpell.create(EntityReference.EVENT_TARGET, GameTag.FROZEN);
		//snowchugger.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(true), freezeSpell));
		snowchugger.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(null), freezeSpell));
		return snowchugger;
	}
}
