package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Counterspell extends SecretCard {

	public Counterspell() {
		super("Counterspell", Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Secret: When your opponent casts a spell, Counter it.");

		Spell counterSpell = new ApplyTagSpell(GameTag.COUNTERED);
		counterSpell.setTarget(EntityReference.PENDING_CARD);
		setTriggerAndEffect(new SpellCastedTrigger(TargetPlayer.OPPONENT), counterSpell);
	}

	@Override
	public int getTypeId() {
		return 57;
	}
}
