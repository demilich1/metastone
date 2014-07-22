package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Avenge extends SecretCard {

	public Avenge() {
		super("Avenge", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When one of your minions dies, give a random friendly minion +3/+2");
		
		Spell buffRandomSpell = new BuffRandomSpell(3, 2);
		buffRandomSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		setTriggerAndEffect(new MinionDeathTrigger(TargetPlayer.SELF), buffRandomSpell);
	}

	@Override
	public int getTypeId() {
		return 385;
	}
}
