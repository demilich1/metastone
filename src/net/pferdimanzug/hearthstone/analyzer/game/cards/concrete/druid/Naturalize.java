package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Naturalize extends SpellCard {

	public Naturalize() {
		super("Naturalize", Rarity.COMMON, HeroClass.DRUID, 1);
		setDescription("Destroy a minion. Your opponent draws 2 cards.");
		SpellDesc drawCardSpell = DrawCardSpell.create(2, TargetPlayer.OPPONENT);
		drawCardSpell.setTarget(EntityReference.NONE);
		setSpell(MetaSpell.create(DestroySpell.create(), drawCardSpell));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 15;
	}
}
