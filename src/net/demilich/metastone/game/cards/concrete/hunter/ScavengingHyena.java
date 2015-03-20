package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ScavengingHyena extends MinionCard {

	public ScavengingHyena() {
		super("Scavenging Hyena", 2, 2, Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Whenever a friendly Beast dies, gain +2/+1.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 43;
	}

	@Override
	public Minion summon() {
		Minion scavengingHyena = createMinion();
		SpellDesc buff = BuffSpell.create(EntityReference.SELF, 2, 1);
		SpellTrigger trigger = new SpellTrigger(new MinionDeathTrigger(TargetPlayer.SELF, Race.BEAST), buff);
		scavengingHyena.setSpellTrigger(trigger);
		return scavengingHyena;
	}
}
