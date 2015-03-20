package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.ArmorGainedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class SiegeEngine extends MinionCard {

	public SiegeEngine() {
		super("Siege Engine", 5, 5, Rarity.RARE, HeroClass.WARRIOR, 5);
		setDescription("Whenever you gain Armor, give this minion +1 Attack.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 609;
	}



	@Override
	public Minion summon() {
		Minion siegeEngine = createMinion();
		SpellDesc gainAttack = BuffSpell.create(EntityReference.SELF, +1, 0);
		SpellTrigger trigger = new SpellTrigger(new ArmorGainedTrigger(), gainAttack);
		siegeEngine.setSpellTrigger(trigger);
		return siegeEngine;
	}
}
