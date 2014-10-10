package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;


public class IceLance extends SpellCard {

	public IceLance() {
		super("Ice Lance", Rarity.COMMON, HeroClass.MAGE, 1);
		setDescription("Freeze a character. If it was already Frozen, deal $4 damage instead.");
		SpellDesc damageSpell = DamageSpell.create(4);
		SpellDesc freezeSpell = ApplyTagSpell.create(GameTag.FROZEN, new TurnStartTrigger());
		setSpell(EitherOrSpell.create(damageSpell, freezeSpell, (context, player, target) -> target.hasStatus(GameTag.FROZEN)));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 65;
	}
}
