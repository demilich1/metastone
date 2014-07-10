package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;


public class IceLance extends SpellCard {

	public IceLance() {
		super("Ice Lance", Rarity.COMMON, HeroClass.MAGE, 1);
		setDescription("Freeze a character. If it was already Frozen, deal $4 damage instead.");
		Spell damageSpell = new DamageSpell(4);
		Spell freezeSpell = new ApplyTagSpell(GameTag.FROZEN, new TurnStartTrigger());
		setSpell(new EitherOrSpell(damageSpell, freezeSpell, (context, player, target) -> target.hasTag(GameTag.FROZEN)));
		setTargetRequirement(TargetSelection.ANY);
	}

}
