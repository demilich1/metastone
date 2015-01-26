package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Gnoll;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;

public class Hogger extends MinionCard {

	public Hogger() {
		super("Hogger", 4, 4, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("At the end of your turn, summon a 2/2 Gnoll with Taunt.");
	}

	@Override
	public int getTypeId() {
		return 141;
	}

	@Override
	public Minion summon() {
		Minion hogger = createMinion();
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), SummonSpell.create(new Gnoll()));
		hogger.setSpellTrigger(trigger);
		return hogger;
	}
}
