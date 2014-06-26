package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HealingTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Lightwarden extends MinionCard {

	public Lightwarden() {
		super("Lightwarden", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever a character is healed, gain +2 Attack.");
	}

	@Override
	public Minion summon() {
		Minion lightwarden = createMinion();
		Spell buffSpell = new BuffSpell(2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new HealingTrigger(), buffSpell);
		lightwarden.setSpellTrigger(trigger);
		return lightwarden;
	}

}
