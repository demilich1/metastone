package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Emboldener3000 extends MinionCard {

	public Emboldener3000() {
		super("Emboldener 3000", 0, 4, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the end of your turn, give a random minion +1/+1.");
		setRace(Race.MECH);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 437;
	}

	@Override
	public Minion summon() {
		Minion emboldener3000 = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.ALL_MINIONS, 1, 1, true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), buffSpell);
		emboldener3000.setSpellTrigger(trigger);
		return emboldener3000;
	}
}
