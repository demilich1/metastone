package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HeroFatalDamageTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class IceBlock extends SecretCard {

	public IceBlock() {
		super("Ice Block", Rarity.EPIC, HeroClass.MAGE, 3);
		setDescription("Secret: When your hero takes fatal damage, prevent it and become Immune this turn.");
		
		SpellDesc iceBlockSpell = ApplyTagSpell.create(GameTag.IMMUNE, new TurnStartTrigger());
		iceBlockSpell.setTarget(EntityReference.FRIENDLY_HERO);
		setTriggerAndEffect(new HeroFatalDamageTrigger(), iceBlockSpell);
	}

	@Override
	public int getTypeId() {
		return 64;
	}
}
