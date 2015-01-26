package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.CloneMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionCardPlayedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class MirrorEntity extends SecretCard {

	public MirrorEntity() {
		super("Mirror Entity", Rarity.COMMON, HeroClass.MAGE, 3);
		setDescription("Secret: When your opponent plays a minion, summon a copy of it.");
		
		SpellDesc copySpell = CloneMinionSpell.create();
		copySpell.setTarget(EntityReference.EVENT_TARGET);
		setTriggerAndEffect(new MinionCardPlayedTrigger(), copySpell);
	}

	@Override
	public int getTypeId() {
		return 68;
	}
}
