package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TargetedBySpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class DragonkinSorcerer extends MinionCard {

	public DragonkinSorcerer() {
		super("Dragonkin Sorcerer", 3, 5, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Whenever you target this minion with a spell, gain +1/+1.");
		setRace(Race.DRAGON);
	}

	@Override
	public Minion summon() {
		Minion dragonkinSorcerer = createMinion();
		SpellDesc buff = BuffSpell.create(EntityReference.SELF, +1, +1);
		SpellTrigger trigger = new SpellTrigger(new TargetedBySpellTrigger(), buff);
		dragonkinSorcerer.setSpellTrigger(trigger);
		return dragonkinSorcerer;
	}

}
