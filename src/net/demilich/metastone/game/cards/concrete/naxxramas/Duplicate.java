package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.PutCopyInHandSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Duplicate extends SecretCard {

	public Duplicate() {
		super("Duplicate", Rarity.COMMON, HeroClass.MAGE, 3);
		setDescription("Secret: When a friendly minion dies, put 2 copies of it in your hand.");

		SpellDesc duplicate = PutCopyInHandSpell.create(EntityReference.EVENT_TARGET, 2);
		setTriggerAndEffect(new MinionDeathTrigger(TargetPlayer.SELF), duplicate);
	}

	@Override
	public int getTypeId() {
		return 389;
	}
	
}
