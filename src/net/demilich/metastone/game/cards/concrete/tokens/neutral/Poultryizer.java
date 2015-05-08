package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Poultryizer extends MinionCard {

	public Poultryizer() {
		super("Poultryizer", 0, 3, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the start of your turn, transform a random minion into a 1/1 Chicken.");
		setRace(Race.MECH);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 448;
	}

	@Override
	public Minion summon() {
		Minion poultryizer = createMinion();
		SpellDesc chickenizeSpell = TransformMinionSpell.create(EntityReference.ALL_MINIONS, new Chicken(), true);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(null), chickenizeSpell);
		poultryizer.setSpellTrigger(trigger);
		return poultryizer;
	}
}
