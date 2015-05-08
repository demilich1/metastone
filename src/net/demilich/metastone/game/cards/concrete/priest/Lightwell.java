package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Lightwell extends MinionCard {
	
	public Lightwell() {
		super("Lightwell", 0, 5, Rarity.RARE, HeroClass.PRIEST, 2);
		setDescription("At the start of your turn, restore 3 Health to a damaged friendly character.");
	}
	
	@Override
	public int getTypeId() {
		return 268;
	}

	@Override
	public Minion summon() {
		Minion lightwell = createMinion();
		SpellDesc healRandomSpell = HealingSpell.create(EntityReference.FRIENDLY_CHARACTERS, 3, true);
		lightwell.setSpellTrigger(new SpellTrigger(new TurnStartTrigger(null), healRandomSpell));
		return lightwell;
	}
}
