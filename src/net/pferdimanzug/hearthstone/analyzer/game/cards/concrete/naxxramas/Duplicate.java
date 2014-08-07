package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.DuplicateSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Duplicate extends SecretCard {

	public Duplicate() {
		super("Duplicate", Rarity.COMMON, HeroClass.MAGE, 3);
		setDescription("Secret: When a friendly minion dies, put 2 copies of it in your hand.");

		SpellDesc duplicate = DuplicateSpell.create();
		duplicate.setTarget(EntityReference.EVENT_TARGET);
		setTriggerAndEffect(new MinionDeathTrigger(TargetPlayer.SELF), duplicate);
	}

	@Override
	public int getTypeId() {
		return 389;
	}
	
}
