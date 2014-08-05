package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HeroFatalDamageTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class IceBlock extends SecretCard {

	public IceBlock() {
		super("Ice Block", Rarity.EPIC, HeroClass.MAGE, 3);
		setDescription("Secret: When your hero takes fatal damage, prevent it and become Immune this turn.");
		
		Spell iceBlockSpell = new ApplyTagSpell(GameTag.IMMUNE, new TurnStartTrigger());
		iceBlockSpell.setTarget(EntityReference.FRIENDLY_HERO);
		setTriggerAndEffect(new HeroFatalDamageTrigger(), iceBlockSpell);
	}

	@Override
	public int getTypeId() {
		return 64;
	}
}
