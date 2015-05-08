package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealToFullSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class StoneskinGargoyle extends MinionCard {

	public StoneskinGargoyle() {
		super("Stoneskin Gargoyle", 1, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("At the start of your turn, restore this minion to full Health.");
	}

	@Override
	public int getTypeId() {
		return 400;
	}

	@Override
	public Minion summon() {
		Minion stoneskinGargoyle = createMinion();
		SpellDesc healSelf = HealToFullSpell.create(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(null), healSelf);
		stoneskinGargoyle.setSpellTrigger(trigger);
		return stoneskinGargoyle;
	}
}
