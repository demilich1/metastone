package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LightningBolt extends SpellCard {

	public LightningBolt() {
		super("Lightning Bolt", Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Deal 3 damage. Overload: (1)");
		setTag(GameTag.OVERLOAD, 1);
		setSpell(new DamageSpell(3));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 325;
	}
}
