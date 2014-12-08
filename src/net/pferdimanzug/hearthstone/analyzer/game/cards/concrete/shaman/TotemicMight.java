package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.filter.RaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TotemicMight extends SpellCard {

	public TotemicMight() {
		super("Totemic Might", Rarity.FREE, HeroClass.SHAMAN, 0);
		setDescription("Give your Totems +2 Health.");
		SpellDesc totemBuff = BuffSpell.create(0, +2);
		totemBuff.setTargetFilter(new RaceFilter(Race.TOTEM));
		totemBuff.setTarget(EntityReference.FRIENDLY_MINIONS);
		setSpell(totemBuff);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 330;
	}

}
