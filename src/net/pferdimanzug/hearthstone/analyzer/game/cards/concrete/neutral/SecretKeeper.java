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

public class SecretKeeper extends MinionCard {

	public SecretKeeper() {
		super("Secret Keeper", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever a Secret is played, gain +1/+1.");
	}

	@Override
	public Minion summon() {
		Minion secretKeeper = createMinion();
		Spell buffSpell = new BuffSpell(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		secretKeeper.setSpellTrigger(new SpellTrigger(new SecretPlayedTrigger(), buffSpell));
		return secretKeeper;
	}

}
