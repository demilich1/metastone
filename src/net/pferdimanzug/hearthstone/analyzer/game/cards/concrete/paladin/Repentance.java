package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SetHpSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionCardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Repentance extends SecretCard {

	public Repentance() {
		super("Repentance", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When your opponent plays a minion, reduce its Health to 1.");

		Spell setHpSpell = new SetHpSpell(1);
		setHpSpell.setTarget(EntityReference.EVENT_TARGET);
		setTriggerAndEffect(new MinionCardPlayedTrigger(), setHpSpell);
	}

}
