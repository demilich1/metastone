package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SecretPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Secretkeeper extends MinionCard {

	public Secretkeeper() {
		super("Secretkeeper", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever a Secret is played, gain +1/+1.");
	}

	@Override
	public int getTypeId() {
		return 194;
	}



	@Override
	public Minion summon() {
		Minion secretkeeper = createMinion();
		Spell buffSpell = new BuffSpell(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		secretkeeper.setSpellTrigger(new SpellTrigger(new SecretPlayedTrigger(), buffSpell));
		return secretkeeper;
	}
}
