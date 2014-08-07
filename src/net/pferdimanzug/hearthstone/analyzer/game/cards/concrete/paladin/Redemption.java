package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReviveMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.OwnMinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Redemption extends SecretCard {

	public Redemption() {
		super("Redemption", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When one of your minions dies, return it to life with 1 Health.");
		
		SpellDesc reviveSpell = ReviveMinionSpell.create(1);
		reviveSpell.setTarget(EntityReference.KILLED_MINION);
		setTriggerAndEffect(new OwnMinionDeathTrigger(), reviveSpell);
	}

	@Override
	public int getTypeId() {
		return 254;
	}
}
