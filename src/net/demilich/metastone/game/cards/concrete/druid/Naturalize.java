package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

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
