package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class FlesheatingGhoul extends MinionCard {

	public FlesheatingGhoul() {
		super("Flesheating Ghoul", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Whenever a minion dies, gain +1 Attack. ");
	}

	@Override
	public int getTypeId() {
		return 128;
	}



	@Override
	public Minion summon() {
		Minion flesheatingGhoul = createMinion();
		Spell buffSpell = new BuffSpell(1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger spellTrigger = new SpellTrigger(new MinionDeathTrigger(), buffSpell);
		flesheatingGhoul.setSpellTrigger(spellTrigger);
		return flesheatingGhoul;
	}
}
