package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class BaronGeddon extends MinionCard {

	public BaronGeddon() {
		super("Baron Geddon", 7, 5, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("At the end of your turn, deal 2 damage to ALL other characters.");
	}

	@Override
	public int getTypeId() {
		return 92;
	}

	@Override
	public Minion summon() {
		Minion baronGeddon = createMinion();
		SpellDesc damageSpell = DamageSpell.create(EntityReference.ALL_OTHER_CHARACTERS, 2);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), damageSpell);
		baronGeddon.setSpellTrigger(trigger);
		return baronGeddon;
	}
}
