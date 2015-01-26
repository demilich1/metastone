package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class YoungPriestess extends MinionCard {

	public YoungPriestess() {
		super("Young Priestess", 2, 1, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("At the end of your turn, give another random friendly minion +1 Health.");
	}

	@Override
	public int getTypeId() {
		return 231;
	}

	@Override
	public Minion summon() {
		Minion youngPriestess = createMinion();
		SpellDesc spell = BuffSpell.create(0, 1);
		spell.setTarget(EntityReference.OTHER_FRIENDLY_MINIONS);
		spell.pickRandomTarget(true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), spell);
		youngPriestess.setSpellTrigger(trigger);
		return youngPriestess;
	}
}
