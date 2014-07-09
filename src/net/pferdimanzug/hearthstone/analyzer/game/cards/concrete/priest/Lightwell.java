package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Lightwell extends MinionCard {
	
	public Lightwell() {
		super("Lightwell", 0, 5, Rarity.RARE, HeroClass.PRIEST, 2);
		setDescription("At the start of your turn, restore 3 Health to a damaged friendly character.");
	}
	
	@Override
	public Minion summon() {
		Minion lightwell = createMinion();
		Spell healRandomSpell = new HealRandomSpell(3);
		healRandomSpell.setTarget(EntityReference.FRIENDLY_CHARACTERS);
		lightwell.setSpellTrigger(new SpellTrigger(new TurnStartTrigger(), healRandomSpell));
		return lightwell;
	}

}
