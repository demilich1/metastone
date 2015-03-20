package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.trigger.DamageSurvivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class GrimPatron extends MinionCard {

	public GrimPatron() {
		super("Grim Patron", 3, 3, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Whenever this minion survives damage, summon another Grim Patron.");
	}

	@Override
	public int getTypeId() {
		return 619;
	}



	@Override
	public Minion summon() {
		Minion grimPatron = createMinion();
		SpellTrigger trigger = new SpellTrigger(new DamageSurvivedTrigger(), SummonSpell.create(new GrimPatron()));
		grimPatron.setSpellTrigger(trigger);
		return grimPatron;
	}
}
