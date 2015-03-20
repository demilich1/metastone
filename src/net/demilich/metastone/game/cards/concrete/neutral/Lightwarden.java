package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HealingTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Lightwarden extends MinionCard {

	public Lightwarden() {
		super("Lightwarden", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever a character is healed, gain +2 Attack.");
	}

	@Override
	public int getTypeId() {
		return 155;
	}

	@Override
	public Minion summon() {
		Minion lightwarden = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, 2, 0);
		SpellTrigger trigger = new SpellTrigger(new HealingTrigger(), buffSpell);
		lightwarden.setSpellTrigger(trigger);
		return lightwarden;
	}
}
