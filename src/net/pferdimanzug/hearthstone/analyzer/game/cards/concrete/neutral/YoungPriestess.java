package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc spell = BuffRandomSpell.create(0, 1);
		spell.setTarget(EntityReference.OTHER_FRIENDLY_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), spell);
		youngPriestess.setSpellTrigger(trigger);
		return youngPriestess;
	}
}
