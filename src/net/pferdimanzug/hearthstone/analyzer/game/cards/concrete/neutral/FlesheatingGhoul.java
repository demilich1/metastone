package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class FlesheatingGhoul extends MinionCard {

	public FlesheatingGhoul() {
		super("Flesheating Ghoul", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Whenever a minion dies, gain +1 Attack. ");
	}

	@Override
	public Minion summon() {
		SpellTrigger spellTrigger = new SpellTrigger(new MinionDeathTrigger(), new BuffSpell(1));
		Minion flesheatingGhoul = createMinion();
		flesheatingGhoul.setSpellTrigger(spellTrigger);
		return flesheatingGhoul;
	}

}
